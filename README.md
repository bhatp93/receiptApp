# receiptApp
To review the code please follow the path
src/main/java/com/fetch/receiptprocessor/Controllers/ReceiptEndpoints.java

The ReceiptEndpoints.java is the controller that receives the Post and Get requests. 
ReceiptEndpoints.java has the following functions 
postReceipt - Accepts post requests
getReceiptPoints - Accepts get requests
calculatePoints - calculates the points for every submitted receipt and stores in receiptDB hashMap.
generateId - Generates random 36 charactered alphanumeric with hyphen string to be used as id
validations- All of the validation related to the post request is handled using this function


