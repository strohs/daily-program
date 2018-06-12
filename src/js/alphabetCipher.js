//Challenge #355 [Easy] Alphabet Cipher

const ASCII_BASE = 97; //lower case letters start at ASCII 97

function buildMatrix() {
    let matrix = [];
    for ( r = 0; r < 26; r++ ) {
        let cols = [];
        for ( c = 0; c < 26; c++ ) {
            let letter = String.fromCharCode( ASCII_BASE + ((c + r) % 26) );
            cols.push( letter );
        }
        matrix.push( cols );
    }
    return matrix;
}

function encode( matrix, key, message ) {
    const encoded = Array.from(message).map( (ch,i) => {
        let ki = (i % key.length);
        return matrix[ ch.charCodeAt(0) - 97 ][ key[ki].charCodeAt(0) - 97 ];
    });
    return encoded.join('');
    
}

const matrix = buildMatrix();
const key = "bond";
const message = "theredfoxtrotsquietlyatmidnight";
let res = encode( matrix, key, message );
console.log( res );
