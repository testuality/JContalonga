package com.testuality.contalonga.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testuality.contalonga.beans.*;

/*
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
*/
import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataModel {
    private List<BankMovement> bankMovementList;
    private List<Expense> expenseList;
    private List<Type> typeList;
    private Map<String, String> typeNames;
    private Map<String, String> subtypeNames;

    public void readDataFromFile(File file) {
        try {

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            System.out.println("***" + sb.toString() + "***");
            reader.close();

            Gson gson = new Gson();
            // Object object = gson.fromJson(new FileReader(file), JsonModel.class);
            Object object = gson.fromJson(sb.toString(), JsonModel.class);
            JsonModel jsonModel = (JsonModel)object;
            System.out.println(jsonModel.getDatetime());
            System.out.println("Expenses " + jsonModel.getExpenses().size());
            System.out.println("Movements " + jsonModel.getBankmovements().size());
            System.out.println("Types " + jsonModel.getTypes().size());

            this.readDataFromJsonModel(jsonModel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromJsonModel(JsonModel jsonModel) {
        this.typeNames = new HashMap<>();
        this.subtypeNames = new HashMap<>();
        this.bankMovementList = new ArrayList<>();
        this.expenseList = new ArrayList<>();
        this.typeList = new ArrayList<>();
        try {
            for (JsonBankMovement jsonMov : jsonModel.getBankmovements()) {
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(jsonMov.getDate());
                GregorianCalendar date = new GregorianCalendar();
                date.setTime(date1);
                this.bankMovementList.add(
                        new BankMovement(jsonMov.getId(), date, jsonMov.getConcept(),
                                jsonMov.getAmount().doubleValue()));
            }

            for (JsonExpense jsonEx : jsonModel.getExpenses()) {
                Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(jsonEx.getDate());
                GregorianCalendar date = new GregorianCalendar();
                date.setTime(date1);
                this.expenseList.add(
                        new Expense(jsonEx.getId(), jsonEx.getMd5(), date, jsonEx.getTypeid(),
                                jsonEx.getSubtypeid(), jsonEx.getDescription(),
                                jsonEx.getAmount().doubleValue()));
            }

            for (JsonType jsonTy : jsonModel.getTypes()) {
                List<Subtype> subTys = new ArrayList<>();
                for (JsonSubtype jsonSty : jsonTy.getSubtypes()) {
                    Subtype st = new Subtype(jsonSty.getId(), jsonSty.getName());
                    subTys.add(st);
                    this.subtypeNames.put(st.getId(), st.getName());
                }
                Type t = new Type(jsonTy.getId(), jsonTy.getName(), subTys);
                this.typeList.add(t);
                this.typeNames.put(t.getId(), t.getName());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTypeName(String typeId) {
        String name = null;
        if (this.typeNames.containsKey(typeId)) {
            name = this.typeNames.get(typeId);
        }
        return name;
    }

    public String getSubtypeName(String subtypeId) {
        String name = null;
        if (this.subtypeNames.containsKey(subtypeId)) {
            name = this.subtypeNames.get(subtypeId);
        }
        return name;
    }

    public void saveDataToFile(File file) {
        /*
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("Hello world");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(file);
            gson.toJson(this.getJsonModel(), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonModel getJsonModel() {
        JsonModel jsonModel = new JsonModel();

        GregorianCalendar now = new GregorianCalendar();
        now.setTimeInMillis(System.currentTimeMillis());
        jsonModel.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<JsonBankMovement> jsonBankMovementList = new ArrayList<>();
        for (BankMovement bankMovement : this.bankMovementList) {
            JsonBankMovement jsonBankMovement = new JsonBankMovement();
            jsonBankMovement.setId(bankMovement.getId());
            jsonBankMovement.setDate(sdf.format(bankMovement.getDate().getTime()));
            jsonBankMovement.setConcept(bankMovement.getConcept());
            jsonBankMovement.setAmount(new BigDecimal(bankMovement.getAmount()));
            jsonBankMovementList.add(jsonBankMovement);
        }
        jsonModel.setBankmovements(jsonBankMovementList);

        List<JsonExpense> jsonExpenseList = new ArrayList<>();
        for (Expense expense : this.expenseList) {
            JsonExpense jsonExpense = new JsonExpense();
            jsonExpense.setId(expense.getId());
            jsonExpense.setMd5(expense.getMd5());
            jsonExpense.setDate(sdf.format(expense.getDate().getTime()));
            jsonExpense.setDescription(expense.getDescription());
            jsonExpense.setTypeid(expense.getTypeId());
            jsonExpense.setSubtypeid(expense.getSubtypeId());
            jsonExpense.setAmount(new BigDecimal(expense.getAmount()));
            jsonExpenseList.add(jsonExpense);
        }
        jsonModel.setExpenses(jsonExpenseList);

        List<JsonType> jsonTypeList = new ArrayList<>();
        for (Type type : this.typeList) {
            JsonType jsonType = new JsonType();
            jsonType.setId(type.getId());
            jsonType.setName(type.getName());

            List<JsonSubtype> jsonSubtypeList = new ArrayList<>();
            for (Subtype subtype : type.getSubtypeList()) {
                JsonSubtype jsonSubtype = new JsonSubtype();
                jsonSubtype.setId(subtype.getId());
                jsonSubtype.setName(subtype.getName());
                jsonSubtypeList.add(jsonSubtype);
            }
            jsonType.setSubtypes(jsonSubtypeList);
            jsonTypeList.add(jsonType);
        }
        jsonModel.setTypes(jsonTypeList);

        return jsonModel;
    }

    public List<Type> getTypes() {
        return this.typeList;
    }

    public List<BankMovement> getBankMovements() {
        return this.bankMovementList;
    }

    public List<Expense> getExpenses() {
        return this.expenseList;
    }

    public void createNewType(String typeName) {
        Type type = new Type(UUID.randomUUID().toString(), typeName, new ArrayList<Subtype>());
        this.typeList.add(type);
    }

    public void createNewSubtype(String typeId, String subtypeName) {
        Type type = null;
        for (Type aux : this.typeList) {
            if (aux.getId().equals(typeId)) {
                type = aux;
                break;
            }
        }
        if (type != null) {
            Subtype subtype = new Subtype(UUID.randomUUID().toString(), subtypeName);
            type.getSubtypeList().add(subtype);
        }
    }

    public void createNewExpense(GregorianCalendar date, String typeId, String subtypeId, String concept, double amount) {
        String id = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf= NumberFormat.getInstance(Locale.UK);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        String aux = sdf.format(date.getTime()) +"#" + typeId + "#" + subtypeId + "#" + concept + "#" + nf.format(amount);
        System.out.println("createNexExpense " + aux);
        Expense expense = new Expense(id, Integer.toString(aux.hashCode()),date, typeId, subtypeId, concept, amount);
        this.expenseList.add(expense);
        this.sortExpenseList();
    }

    private void sortExpenseList() {
        this.expenseList.sort(new ExpenseComparator());
    }

    public double getTotalExpenseAmountByYear(int year) {
        double total = 0.0;
        for (Expense expense : this.expenseList) {
            if (expense.getDate().get(Calendar.YEAR) == year) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public ReportForYear getReportForYear(int year) {

        double total = 0.0;
        for (Expense expense : this.expenseList) {
            if (expense.getDate().get(Calendar.YEAR) == year) {
                total += expense.getAmount();
            }
        }
        ReportForYear rbt = new ReportForYear(year, total);

        for (Type type : this.typeList) {
            double typeTotal = 0.0;
            for (Expense expense : this.expenseList) {
                if (expense.getDate().get(Calendar.YEAR) == year && expense.getTypeId().equals(type.getId())) {
                    typeTotal += expense.getAmount();
                }
            }
            double percent =  typeTotal * 100.0/ total;
            ReportItem item = new ReportItem(type, null, typeTotal, percent);
            rbt.getByTypeList().add(item);
        }

        for (Type type : this.typeList) {
            for (Subtype subtype : type.getSubtypeList()) {
                double typesubtypeTotal = 0;
                for (Expense expense : this.expenseList) {
                    if (expense.getDate().get(Calendar.YEAR) == year &&
                        expense.getTypeId().equals(type.getId()) &&
                        expense.getSubtypeId().equals(subtype.getId())) {
                        typesubtypeTotal += expense.getAmount();
                    }
                }
                double percent = typesubtypeTotal * 100.0 / total;
                ReportItem item = new ReportItem(type, subtype, typesubtypeTotal, percent);
                rbt.getByTypeSubtypeList().add(item);
            }
        }
        return rbt;
    }

    public ReportForType getReportForType(Type type) {
        double total = 0.0;
        for (Expense expense : this.expenseList) {
            if (expense.getTypeId().equals(type.getId())) {
                total += expense.getAmount();
            }
        }

        ReportForType rft = new ReportForType(type, total);
        int yearIni = this.expenseList.get(0).getDate().get(Calendar.YEAR);
        int yearEnd = this.expenseList.get(this.expenseList.size() - 1).getDate().get(Calendar.YEAR) + 1;

        for (int year = yearIni; year <= yearEnd; year++) {
            double yearTotal = 0.0;
            for (Expense expense : this.expenseList) {
                if (expense.getDate().get(Calendar.YEAR) == year && expense.getTypeId().equals(type.getId())) {
                    yearTotal += expense.getAmount();
                }
            }
            double percent =  yearTotal * 100.0/ total;
            ReportItem item = new ReportItem(type, null, yearTotal, percent);
            rft.getTypeList().add(item);
        }

        for (Subtype subtype : type.getSubtypeList()) {
            List<ReportItem> list = new ArrayList<>();
            for (int year = yearIni; year <= yearEnd; year++) {
                double yearTotal = 0.0;
                for (Expense expense : this.expenseList) {
                    if (expense.getDate().get(Calendar.YEAR) == year &&
                            expense.getTypeId().equals(type.getId()) &&
                            expense.getSubtypeId().equals(subtype.getId())) {
                        yearTotal += expense.getAmount();
                    }
                }
                double percent =  yearTotal * 100.0/ total;
                ReportItem item = new ReportItem(type, subtype, yearTotal, percent);
                list.add(item);
            }
            rft.getSubtypeLists().add(list);
        }
        return rft;
    }
}

class ExpenseComparator implements Comparator<Expense> {
    @Override
    public int compare(Expense o1, Expense o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}

class JsonModel {
    private String datetime;
    private List<JsonExpense> expenses;
    private List<JsonBankMovement> bankmovements;
    private List<JsonType> types;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<JsonExpense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<JsonExpense> expenses) {
        this.expenses = expenses;
    }

    public List<JsonBankMovement> getBankmovements() {
        return bankmovements;
    }

    public void setBankmovements(List<JsonBankMovement> bankmovements) {
        this.bankmovements = bankmovements;
    }

    public List<JsonType> getTypes() {
        return types;
    }

    public void setTypes(List<JsonType> types) {
        this.types = types;
    }
}

class JsonType {
    private String id;
    private String name;
    private List<JsonSubtype> subtypes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonSubtype> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(List<JsonSubtype> subtypes) {
        this.subtypes = subtypes;
    }
}

class JsonSubtype {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}