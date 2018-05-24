//Challenge #361 [Easy] Tally Program
const in1 = 'abcde';
const in2 = 'dbbaCEDbdAacCEAadcB';
const in3 = "EbAAdbBEaBaaBBdAccbeebaec";

function tallyPoint(ch) {
    return (ch === ch.toLowerCase()) ? 1 : -1;
}

/**
 * converts input string into a Map. The keys of the map are the lowercase characters of the inStr. The values are
 * arrays containing the case insensitive characters that equal the key: e.g.  ['a', [a,a,A,A,A] ]
 * @param inStr
 * @returns {Map<String, Array<String>>}
 */
function mapInput( inStr ) {
    const splitstr = inStr.split('');
    return splitstr.reduce( (m, ch) => {
        if ( m.has( ch.toLowerCase() ) ) {
            m.get(ch.toLowerCase()).push(ch);
        } else {
            m.set( ch.toLowerCase(), new Array(ch) );
        }
        return m;
    }, new Map());
}

const tallyMap = mapInput(in3);
const pointArr = Array.from( tallyMap ).map( ([key,values]) => [key, values.reduce( (acc,e ) => acc + tallyPoint(e), 0)]);
pointArr.sort((a, b) => b[1] - a[1] );
pointArr.forEach( ([ch,points]) => console.log(`${ch}::${points}`));


