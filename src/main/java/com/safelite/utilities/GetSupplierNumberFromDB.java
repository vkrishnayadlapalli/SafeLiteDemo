package com.safelite.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
public class GetSupplierNumberFromDB {

    public static void main(String testConfig[]) {
        int rowCount = 0;
        String[] productID = new String[rowCount];
        String[] appIDs = {"1796","82"};

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Oracle JDBC Driver Registered!");
            Connection connection = null;
            JSONObject obj = new JSONObject();
            JSONObject ntdSite = new JSONObject();
            JSONObject atdSite = new JSONObject();
            JSONObject atdSite2 = new JSONObject();
            JSONObject atdsupplierNumbers = new JSONObject();
            JSONObject ntdsupplierNumbers = new JSONObject();

            connection = DriverManager.getConnection("jdbc:oracle:thin:@exdb02-vip.test.icd:1521/EBSQA",
                    "soauser", "soauser_123");
            Statement getProductsWithInventory = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            for (String appID : appIDs) {
                ResultSet getProductsWithInventoryResults = getProductsWithInventory.executeQuery(
                        "Select msi.attribute1 \"Legacy Item Number\",msi.segment1 OracleNumber, sum(primary_transaction_quantity) quantity,mc.segment2 product_group,msi.unit_weight , msi.unit_volume, msi.DESCRIPTION from apps.mtl_onhand_quantities_detail moqd, apps.mtl_parameters mp,apps.mtl_system_items_b msi,apps.mtl_item_categories mic ,apps.mtl_categories mc ,apps.mtl_category_sets mcs where moqd.organization_id=mp.organization_id and msi.segment1 NOT LIKE 'A%'and rownum <=20 AND mp.organization_id IN (SELECT organization_id_child FROM (SELECT DISTINCT pos.d_child_name,pos.organization_id_child,CONNECT_BY_ISCYCLE cyclecol FROM apps.per_org_structure_elements_v pos,apps.per_org_structure_versions_v posve, apps.per_organization_structures_v posv WHERE 1 = 1 AND pos.org_structure_version_id = posve.org_structure_version_id AND posv.organization_structure_id = posve.organization_structure_id AND posv.name = 'ATDI REPORTING HIERARCHY' AND LEVEL = 4 "
                                + "START WITH pos.organization_id_child = APPS.XXATD_COMMON_UTILS_PKG.GET_ITEM_VALIDATION_ORG('"+appID+"') CONNECT BY NOCYCLE PRIOR pos.organization_id_child = pos.organization_id_parent))and msi.organization_id = moqd.organization_id and msi.inventory_item_id = moqd.inventory_item_id and moqd.subinventory_code in ('STOCK') and msi.inventory_item_id = mic.inventory_item_id and msi.organization_id = mic.organization_id and mic.category_id = mc.category_id and mic.category_set_id = mcs.category_set_id and mcs.category_set_name='VGS_TRIPLE_SEGMENT' and primary_transaction_quantity > 100 group by moqd.inventory_item_id,msi.segment1,mc.segment2,msi.unit_weight , msi.unit_volume, msi.DESCRIPTION, msi.attribute1");
                getProductsWithInventoryResults.beforeFirst();
                rowCount = getProductsWithInventoryResults.last() ? getProductsWithInventoryResults.getRow() : 0;
                System.out.println("rowCount: " + rowCount);
                productID = new String[rowCount];
                int i = 0;
                getProductsWithInventoryResults.beforeFirst();
                while (getProductsWithInventoryResults.next()) {
                    productID[i] = String.valueOf(getProductsWithInventoryResults.getString("Description"));
                    System.out.println("Description: "+productID[i]);
                    String productId = String.valueOf(getProductsWithInventoryResults.getString("ORACLENUMBER"));
                    System.out.println("OracleNum: "+productId);
                    productID[i] = productID[i].split(" ")[0];
                    if(productID[i].length() < 4 || productID[i].contains("/") || productID[i].contains("X")){
                        productID[i] = productId;
                    }
                    System.out.println("productID: " + productID[i]);
                    i++;
                }
                System.out.println("rowCount Json: " + rowCount);
                if(appID == "1796"){
                    for (int i1 = 0; i1 < productID.length; i1++) {
                        if(!productID[i1].contains("."))
                            ntdsupplierNumbers.put(String.valueOf(i1+1), String.valueOf(productID[i1]) + "");
                    }
                    //obj.put("NTDConnect", ntdSite);
                  //  ntdSite.put("SupplierNumbers", ntdsupplierNumbers);
                }
                else if(appID == "82"){
                    for (int i1 = 0; i1 < productID.length; i1++) {
                        if(!productID[i1].contains("."))
                            atdsupplierNumbers.put(String.valueOf(i1+1), String.valueOf(productID[i1]) + "");
                    }
                   // obj.put("ATDOnline", atdSite);
                    obj.put("ATDOnline2.0", atdSite2);
                    //atdSite.put("SupplierNumbers", atdsupplierNumbers);
                    atdSite2.put("SupplierNumbers", atdsupplierNumbers);
                }
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(obj.toString());
            String prettyJsonString = gson.toJson(je);
            try (FileWriter file = new FileWriter(System.getProperty("user.dir")+"//TestData//masterData.json")) {
                file.write(prettyJsonString);
                System.out.println("Successfully Copied JSON Object to File ...");
                System.out.println("\nJSON Object: " + prettyJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        catch (SQLException e) {
            System.out.println("Query Execution Failed! Check output console");
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            System.out.println("JSONException");
            e.printStackTrace();
        }

    }
}



