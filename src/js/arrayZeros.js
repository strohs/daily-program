// given an array of integers, move all zeros to the end of the array

const arr1 = [1,2,3,0,5,6,7,0,0,3,5,6,7];
const arr2 = [1,2,3,0,0,0];


function arrayZeros( arr ) {
    //this approach goes through the array in reverse order, pushing zeros onto the new array and using unshift
    //to push non-zero integers onto the front of the array
    let newarr = [];
    for (let i = arr.length - 1; i >= 0; i--) {
        if ( arr[i] === 0 )
            newarr.push( arr[i] );
        else
            newarr.unshift( arr[i] );
    }
    return newarr;
}

let res = arrayZeros( arr1 );
console.log( res );

