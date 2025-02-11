
// 1. Reverse a string
function reverseString(str) {
    // Convert string to array, reverse it, and join back to string
    // This is more efficient for modern JavaScript
    return str.split('').reverse().join('');
    
}

// 2. Find the second-largest number in an array.
function findSecondLargest(arr) {
    // Handle edge cases
    if (!arr || arr.length < 2) {
        return "Array should have at least 2 elements";
    }
    
    // Remove duplicates and sort in descending order
    const uniqueSorted = [...new Set(arr)].sort((a, b) => b - a);
    
    // Check if we have at least 2 unique numbers
    if (uniqueSorted.length < 2) {
        return "Array should have at least 2 different numbers";
    }
    
    // Return second element (second-largest)
    return uniqueSorted[1];
}

// 3. Check if a given string is a palindrome.
function isPalindrome(str) {
    // Convert to lowercase and remove non-alphanumeric characters
    const cleanStr = str.toLowerCase().replace(/[^a-z0-9]/g, '');
    
    // Compare string with its reverse
    return cleanStr === cleanStr.split('').reverse().join('');
}



// Run these tests to see the output
console.log("1. String Reversal Tests:");
console.log(reverseString("hello")); 

console.log("\n2. Second Largest Number Tests:");
console.log(findSecondLargest([10, 5, 8, 12, 3])); // 10
console.log(findSecondLargest([1, 1, 1, 1])); 
console.log(findSecondLargest([7])); 

console.log("\n3. Palindrome Tests:");
console.log(isPalindrome("A man, a plan, a canal: Panama")); 
console.log(isPalindrome("race a car")); 