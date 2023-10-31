package com.fetch.receiptprocessor.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

@RestController
public class ReceiptEndpoints {

    HashMap<String, Integer> receiptDB = new HashMap<>();
    @PostMapping(path="/receipts/process")
    public ResponseEntity<ResponseId> postReceipt(@RequestBody Receipt receipt){
        if(!validations(receipt))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        String id = "";
        do{
            id = generateId();
        }while(receiptDB.containsKey(id));
        int totalPoints = calculatePoints(receipt);
        receiptDB.put(id,totalPoints);
        ResponseId responseId = new ResponseId();
        responseId.setId(id);
        return new ResponseEntity(responseId, HttpStatus.CREATED);
    }

    @GetMapping(path = "/receipts/{id}/points")
    public ResponseEntity<ResponsePoints> getReceiptPoints(@PathVariable String id){
        if(!receiptDB.containsKey(id))
            return new ResponseEntity("description: no receipt found for that id", HttpStatus.NOT_FOUND);

        int points = receiptDB.get(id);
        ResponsePoints responsePoints = new ResponsePoints();
        responsePoints.setPoints(points);
        return new ResponseEntity(responsePoints, HttpStatus.OK);
    }

    public int calculatePoints(Receipt receipt){
        int totalPoints = 0;
        String retailerName = receipt.getRetailer();
        for(int i=0; i<retailerName.length();i++){
            char c = retailerName.charAt(i);
            if(Character.isAlphabetic(c) || Character.isDigit(c))
                totalPoints++;
        }

        double receiptTotal = Double.parseDouble(receipt.getTotal());
        if(receiptTotal % 1 == 0)
            totalPoints = totalPoints + 50;

        if(receiptTotal % 0.25 == 0)
            totalPoints = totalPoints + 25;
        int numberOfItems = receipt.getItems().length;
        totalPoints = totalPoints + (numberOfItems/2 * 5);

        for(int i=0; i<receipt.getItems().length;i++){
            String shortDescription = receipt.getItems()[i].getShortDescription();
            shortDescription = shortDescription.trim();
            if(shortDescription.length() % 3 ==0){
                double price = Double.parseDouble(receipt.getItems()[i].getPrice());
                double points = Math.ceil(price * 0.2);
                totalPoints = totalPoints + (int)points;
            }
        }
        LocalDate purchaseDate = receipt.getPurchaseDate();
        int day = purchaseDate.getDayOfMonth();
        if(day % 2 != 0)
            totalPoints = totalPoints + 6;
        int purchaseTime = receipt.getPurchaseTime().getHour();
        if(purchaseTime>=14 && purchaseTime <=16 )
            totalPoints = totalPoints  + 10;
        return totalPoints;
    }

    public String generateId(){
        char[] characters ="abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

        StringBuilder generatedId = new StringBuilder();
        Random  r = new Random();
        for(int i =1; i <= 36; i++) {
            if(i%9 ==0 && i!=36)
                generatedId.append("-");
            else{
                int index = (int) (r.nextInt(characters.length));
                generatedId.append(characters[index]);
            }
        }
        return generatedId.toString();
    }

    public boolean validations(Receipt receipt){
        if(receipt.getRetailer() == null || receipt.getRetailer().isEmpty())
            return false;
        if(receipt.getPurchaseDate() == null || receipt.purchaseTime == null)
            return false;
        if(receipt.getItems() == null)
            return false;
        for(int i=0; i < receipt.getItems().length;i++){
            String price = receipt.getItems()[i].getPrice();
            if(price.isEmpty())
                return false;
            for(int j=0; j<price.length();j++){
                if(!Character.isDigit(price.charAt(j)) && price.charAt(j) != '.' )
                    return false;
            }
            if(receipt.getItems()[i].getShortDescription() == null || receipt.getItems()[i].getShortDescription().isEmpty())
                return false;
        }

        String total = receipt.getTotal();
        if(total == null || total.isEmpty())
            return false;
        for(int i=0; i<total.length();i++){
            if(!Character.isDigit(total.charAt(i)) && total.charAt(i) != '.' )
                return false;
        }

        return true;
    }
}
