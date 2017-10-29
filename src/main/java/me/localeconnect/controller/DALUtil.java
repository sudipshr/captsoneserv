package me.localeconnect.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import me.localeconnect.model.User;

public class DALUtil {
	
	
	public static boolean createTable(Class<?> modelClassz) {
		try {
		DynamoDBMapper mapper = getDynamoDBMapper();
		
		CreateTableRequest tableRequest =     mapper.generateCreateTableRequest(modelClassz); // 1
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1000L, 1500L)); // 2
		List<GlobalSecondaryIndex> inxs = tableRequest.getGlobalSecondaryIndexes();
		if (inxs != null){
			for (GlobalSecondaryIndex idx: inxs){
				idx.setProvisionedThroughput(new ProvisionedThroughput(10l, 10l));
			}
			
		}
		getClient().createTable(tableRequest); // 3

		    } catch (Error e) {
		        e.printStackTrace();
		        return false;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		    return true;
		}
	
	
	public static DynamoDB getDynamoDB() {

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2")).build();

		DynamoDB dynamoDB = new DynamoDB(client);

		return dynamoDB;

	}

	public static AmazonDynamoDB getClient() {
		return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2")).build();
	}

	public static DynamoDBMapper getDynamoDBMapper() {

		AmazonDynamoDB client = getClient();

		DynamoDBMapper dynamoDB = new DynamoDBMapper(client);

		return dynamoDB;

	}
	
	
	public static void createTableHelper(String tableName, long readCapacityUnits, long writeCapacityUnits,
			String partitionKeyName, String partitionKeyType, String sortKeyName, String sortKeyType) {

		try {

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
																													// key

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(
					new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));

			if (sortKeyName != null) {
				keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort
																													// key
				attributeDefinitions
						.add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));
			}

			CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
					.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
							.withWriteCapacityUnits(writeCapacityUnits));

			request.setAttributeDefinitions(attributeDefinitions);

			System.out.println("Issuing CreateTable request for " + tableName);
			Table table = getDynamoDB().createTable(request);
			System.out.println("Waiting for " + tableName + " to be created...this may take a while...");
			table.waitForActive();

		} catch (Exception e) {
			System.err.println("CreateTable request failed for " + tableName);
			System.err.println(e.getMessage());
		}
	}
	
	public static void listMyTables() {

        TableCollection<ListTablesResult> tables = getDynamoDB().listTables();
        Iterator<Table> iterator = tables.iterator();

        System.out.println("Listing table names");

        while (iterator.hasNext()) {
            Table table = iterator.next();
            System.out.println(table.getTableName());
        }
    }

    public static void getTableInformation(String tableName) {

        System.out.println("Describing " + tableName);

        TableDescription tableDescription = getDynamoDB().getTable(tableName).describe();
        System.out.format(
            "Name: %s:\n" + "Status: %s \n" + "Provisioned Throughput (read capacity units/sec): %d \n"
                + "Provisioned Throughput (write capacity units/sec): %d \n",
            tableDescription.getTableName(), tableDescription.getTableStatus(),
            tableDescription.getProvisionedThroughput().getReadCapacityUnits(),
            tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
    }

    public static void updateExampleTable(String tableName) {

        Table table = getDynamoDB().getTable(tableName);
        System.out.println("Modifying provisioned throughput for " + tableName);

        try {
            table.updateTable(new ProvisionedThroughput().withReadCapacityUnits(6L).withWriteCapacityUnits(7L));

            table.waitForActive();
        }
        catch (Exception e) {
            System.err.println("UpdateTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }

    public static void deleteExampleTable(String tableName) {

        Table table = getDynamoDB().getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();

            System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");

            table.waitForDelete();
        }
        catch (Exception e) {
            System.err.println("DeleteTable request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }
    
    public static void deleteModelTable(Class<?> classz) {

        DeleteTableRequest req = getDynamoDBMapper().generateDeleteTableRequest(classz);
        try {
        	String tableName = req.getTableName();
            System.out.println("Issuing DeleteTable request for " + tableName);
            deleteExampleTable(tableName);
            
            
        }
        catch (Exception e) {

            System.err.println(e.getMessage());
        }
    }
	
	public static void main(String args[]){
		createTable(User.class);
		//createTable(Message.class);
		//createTable(Event.class);
		//createTable(Preference.class);
		
		//deleteModelTable(User.class);
	}

}
