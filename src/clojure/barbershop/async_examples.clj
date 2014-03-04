(ns barbershop.async-examples
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
                             (:require [clojure.core.async :refer :all :as async]
                                       [clojure.pprint :refer [pprint]]))

;; The most basic primitive in Core.Async is the channel

(def c (chan))


(chan)
;; We can attach listeners via take!

(take! c (fn [v] (println v)))


;; And we can send values via put!

(put! c 42)


;; And in reverse order

(put! c "Hello World")

(take! c (fn [v] (println "Got " v)))

;; The semantics are simple. Callbacks are one-shot. And you can have
;; many readers/writers. Each puts/gets a single value. There is no fan-out
;; or fan-in.



;; Notice the different location of the output, one
;; side or the other dispatches to a threadpool to
;; run the attached callback.

;; We can wait until the put is finished by passing a callback

(put! c "Hello World" (fn [] (println "Done putting")))


(take! c (fn [v] (println "Got " v)))

;; Try the above again in reverse order to show dispatching



;;;;;;; Introducing <!! and >!! ;;;;;;

;; Well that's nice, but it's a pain to write code in that form,
;; so let's use something that uses promises:


(defn takep [c]
  (let [p (promise)]
    (take! c (fn [v] (deliver p v)))
    @p))

;; Now we can block the current thread waiting on the promise.
(future (println "Got!" (takep c)))

(put! c 42)


;; And we can do the reverse with put!

(defn putp [c val]
  (let [p (promise)]
    (put! c val (fn [] (deliver p nil)))
    @p))


(future (println "Done" (putp c 42)))

(future (println "Got!" (takep c)))


;; Well, that's exactly what clojure.core.async/<!! and >!! do

(future (println "Done" (>!! c 42)))

(future (println "Got! " (<!! c)))


;; But future doesn't really fit here, as it returns a promise, why not make
;; it return a channel? This is what core.async's thread macro does:

(thread 42)

(<!! (thread 42))

(thread (println "It works!" (<!! (thread 42))))

;;;;;;;; The Go Block ;;;;;;;;

;; That's all well and good, but who wants to tie up a thread
;; when we could use simple callbacks. This is what the go macro
;; does. It lets you write code that looks like the above code,
;; but it re-writes all your code to use callbacks.

(go 42)

(<!! (go 42))

(go (println "It works!" (<! (go 42))))

;; Wait....why did we use <!! Well <!! is simply a function that uses
;; blocking promises to wait for channel values. That would mess up the fixed
;; size thread pool that go blocks dispatch in. So we must define two sets
;; of operations

;; for thread, use <!! and >!! (blocking)

;; for go, use <! and >! (parking)



;; How do go blocks work? Let's take a look.

(pprint (macroexpand '(go 42)))

;; The code is rewritten as a state machine. Each block ends with a flow
;; control command to the next block. From this we can start/stop the state
;; machine as desired.

(pprint (macroexpand '(go (>! c (inc (<! c))))))


;; You really don't have to worry about most of this. Just accept that it works
;; and look up clojure core.async state machines on the internet if you want
;; more information.


;;;;; Buffer Types ;;;;;


;; Fixed length buffer
(def fbc (chan 1))

(go (>! fbc 1)
    (println "done 1"))
(go (>! fbc 2)
    (println "done 2"))

(<!! fbc)
(<!! fbc)


;; Dropping buffer (aka. drop newest)

(def dbc (chan (dropping-buffer 1)))

(go (>! dbc 1)
    (println "done 1"))

(go (>! dbc 2)
    (println "done 2"))

(<!! dbc) ;; returns 1

;; Sliding buffer (aka. drop oldest)

(def sbc (chan (sliding-buffer 1)))

(go (>! sbc 1)
    (println "done 1"))

(go (>! sbc 2)
    (println "done 2"))

(<!! sbc) ;; returns 2

;;; Closing a channel

(def c (chan))

(close! c)

(<!! c)

;;;; Alt & Timeout ;;;;

;; Sometimes we want to take the first available item from a bunch
;; of channels. For this we use alt! and alt!!

(def a (chan))
(def b (chan))

(put! a 42)

(alts!! [a b]) ;; returns [value chan]

;; Timeout is a channel that closes after X ms
;; (close! (chan 42))

(<!! (timeout 2000))

;; Often used with alt

(alts!! [a (timeout 1000)])

;; Alts can be used with writes

(alts!! [[a 42]
         (timeout 1000)])

;; We can also provide defaults for alts

(alts!! [a]
        :default :nothing-found)

;; By default, alts are tried in random order

(put! a :a) ;; Do this a few times
(put! b :b) ;; And this

(alts!! [a b]) ;; Notice the order


;; And again with :priority true

(put! a :a)
(put! b :b)

(alts!! [a b]
        :priority true)


;;;;; Logging Handler ;;;;;

(def log-chan (chan))

(defn create-log-chan []
  (let [log-chan (chan)]
    (thread
      (loop []
        (when-let [v (<!! log-chan)]
          (println v)
          (recur)))
      (println "Log Closed"))
    log-chan))

(thread
  (loop []
    (when-let [v (<!! log-chan)]
      (println v)
      (recur)))
  (println "Log Closed"))


(close! log-chan)

(defn log [msg]
  (>!! log-chan msg))

(log "foo")

