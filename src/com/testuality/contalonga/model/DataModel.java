package com.testuality.contalonga.model;

import com.testuality.contalonga.beans.BankMovement;
import com.testuality.contalonga.beans.Expense;
import com.testuality.contalonga.beans.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataModel {
    private List<BankMovement> bankMovementList;
    private List<Expense> expenseList;
    private List<Type> typeList;


    public void readDataFromFile(File file) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            System.out.println(sb.toString());
            reader.close();

            // https://code.google.com/archive/p/json-simple/wikis/DecodingExamples.wiki
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(sb.toString());
            if (obj instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) obj;
                JSONArray jsonMovements = (JSONArray) jsonObj.get("bankmovements");
                JSONArray jsonExpenses = (JSONArray) jsonObj.get("expenses");
                JSONArray jsonTypes = (JSONArray) jsonObj.get("types");

                List<BankMovement> bmList = this.parseJsonBankMovements(jsonMovements);
                List<Expense> expList = this.parseJsonExpenses(jsonExpenses);
                List<Type> tyList = this.parseJsonTypes(jsonTypes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private List<BankMovement> parseJsonBankMovements(JSONArray jsonMovements) {
        List<BankMovement> outList = new ArrayList<>();
        for (Object obj : jsonMovements) {
            JSONObject jsonMovement = (JSONObject) obj;
            jsonMovement.get("id");
        }
        return outList;
    }

    private List<Expense> parseJsonExpenses(JSONArray jsonExpenses) {
        List<Expense> outList = new ArrayList<>();
        return outList;
    }

    private List<Type> parseJsonTypes(JSONArray jsonTypes) {
        List<Type> outList = new ArrayList<>();
        return outList;
    }

    public void saveDataToFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("Hello world");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
