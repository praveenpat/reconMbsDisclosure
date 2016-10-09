package com.compare.doc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import au.com.bytecode.opencsv.CSVReader;

public class CompareDocs {
	
	
	
	private static String[] fieldMapping ={"Security Type","Pool Number","CUSIP","Pool Prefix","Issue Date","Maturity Date","Original Balance","Status","Current Security Balance","Issuance Pool Loan Count","Current Pool Loan Count","Issuance Average Loan Size","WA Coupon_Issuance","WA Coupon_Current","WA Maturity_Issuance","WA Maturity_Current","WA Credit Score_Issuance","WA Credit Score_Current","WA Loan To Value_Issuance","WA Loan To Value_Current","WA Combined LTV_Issuance","WA Combined LTV_Current","WA Loan Age_Issuance","WA Loan Age_Current","WA Loan Term_Issuance","WA Loan Term_Current","WA Months Remaining To Scheduled Amortization_Issuance","WA Months Remaining To Scheduled Amortization_Current","Third Party Origination_Issuance","Third Party Origination_Current","Missing Credit Score_Issuance","Missing Credit Score_Current","Missing LTV_Issuance","Missing LTV_Current","Interest Only with Same Month Pooling_Issuance","Interest Only with Same Month Pooling_Current","Fully Amortizing with Same Month Pooling_Issuance","Fully Amortizing with Same Month Pooling_Current","Transfer Type","Pool Subtype","Pass Through Method","MBS Margin_Issuance","MBS Margin_Current","Accrual Rate_Issuance","Accrual Rate_Current","WA Loan Margin_Issuance","WA Loan Margin_Current","WA Life Floor_Issuance","WA Life Floor_Current","WA Life Cap_Issuance","WA Life Cap_Current","WA Months To Roll_Issuance","WA Months To Roll_Current","WA Max Pool Accrual Rate_Issuance","WA Max Pool Accrual Rate_Current","WA Min Pool Accrual Rate_Issuance","WA Min Pool Accrual Rate_Current","Convertible Flag","Minimum Index Movement","First Rate Change Date","First Payment Change Date","Per-adjustment Rate Cap","Payment Cap","Standard","Variable","Deferred Interest Flag","Weighted Avg. Neg. Amortization Limit","Rate Adjustment Frequency","Payment Change Frequency"};
	

	
	
	
	
	public List<ComparedResults> getCompareResults(String cusipId, CommonsMultipartFile csvFile) throws Exception{
		
	
		System.out.println(cusipId);
		
		
		
		
		Document doc = Jsoup.connect("https://mbsdisclosure.fanniemae.com/PoolTalk2/securityDetails.html?cusip="+cusipId).get();
		
		
		
		
		Element elementById = doc.body().getElementById("securityHeader");
		Elements tables = elementById.getElementsByTag("table");
		
		Map <String,String> keyVals= new HashMap<String,String>(); 
		tables.forEach(ele -> {
			
			Elements tableHeaders = ele.getElementsByTag("th");			
			Elements tableValues = ele.getElementsByTag("td");
			for(int i=0;i<tableHeaders.size();i++){
				
				keyVals.put(tableHeaders.get(i).text(), tableValues.get(i).text().trim());
			}
			
			
		});
		
		
		
		
		
		Element mbsdiv = doc.body().getElementById("mbs");
		Elements mbsTables = mbsdiv.getElementsByTag("table");
		
		//System.out.println(mbsTables.get(13));
		
		
		

		Consumer<Element> extractkeyVal= tr-> {
		    Elements tds= tr.getElementsByTag("td");
		    String key=tds.get(0).getElementsByTag("strong").get(0).text();
		    String value=tds.get(1).text().trim();
		    keyVals.put(key, value.trim());
		};
		
		
		Consumer<Element> issue_Curr_funct =  tr-> {
		    Elements tds= tr.getElementsByTag("td");
		    String key=tds.get(0).getElementsByTag("strong").get(0).text();
		    String value1=tds.get(1).text();
		    String value2=tds.get(2).text();
		    
		    keyVals.put(key+"_Issuance", value1);
		    keyVals.put(key+"_Current", value2);
	
	    };
	    
	    
	    
				
		Elements secInfoTableRows = mbsTables.get(2).getElementsByTag("tr");
        secInfoTableRows.forEach(extractkeyVal);
		
        Elements secWaInfoRows = mbsTables.get(4).getElementsByTag("tr");
	    secWaInfoRows.remove(0);
	    secWaInfoRows.forEach(issue_Curr_funct);
	    
	    Elements perUPBrows = mbsTables.get(5).getElementsByTag("tr");
	    perUPBrows.remove(0);
	    perUPBrows.forEach(issue_Curr_funct);
		
        
        
		
		Elements ARMGeneralRows = mbsTables.get(7).getElementsByTag("tr");
		
		ARMGeneralRows.forEach(tr-> {
			    Elements tds= tr.getElementsByTag("td");
			    String key=tds.get(0).getElementsByTag("strong").get(0).text();
			    String value=tds.get(1).text().trim();
			    
			    if(value.contains("-") && value.length()>1){
			    	
			    	value =value.substring(0,value.indexOf("-"));
			    }
			    
			    
			    keyVals.put(key, value.trim());
		
		    }
				
				
				);
		
		Elements rateInformationRows = mbsTables.get(8).getElementsByTag("tr");
		rateInformationRows.remove(0);		
		rateInformationRows.forEach(issue_Curr_funct);
		
		Elements armWaInfoRows = mbsTables.get(9).getElementsByTag("tr");
		armWaInfoRows.remove(0);
		armWaInfoRows.forEach(issue_Curr_funct);
		
		
		
		Elements adjGenRows = mbsTables.get(11).getElementsByTag("tr");
		adjGenRows.forEach(extractkeyVal);
		
		
		Elements adjCaps = mbsTables.get(12).getElementsByTag("tr");
		adjCaps.remove(2);
		adjCaps.forEach(extractkeyVal);
		
		Elements negAmort = mbsTables.get(13).getElementsByTag("tr");	
		negAmort.remove(2);
		negAmort.forEach(extractkeyVal);
		
		
		
		
		
		Map <String,String> formattedKeyVals= new HashMap<String,String>(); 		
		
		keyVals.forEach((key,val) -> {
			
			
			boolean isamount=false;
			if(val.contains("$")){
				
				val=val.replace("$","").replaceAll(",", "");
				isamount=true;
			}
			
				
				String newVal=val;
			
			if(StringUtils.isAlphanumeric(val)){
				if(val.contains("$")){
					 newVal=val.replace("$", "").replaceAll(",","");
					if(isNumeric(newVal)){
						
						newVal=String.valueOf(Double.parseDouble(newVal));
						
					}
				}				
				
				formattedKeyVals.put(key, newVal);
			}else if (!isamount && isNumeric(val)){
				
				formattedKeyVals.put(key, String.valueOf(Double.parseDouble(val)));
			}
			
			else {
				
				formattedKeyVals.put(key, val);
			}
			
			
			
			
			
		});
		
		
		
		System.out.println("****************html key values **********************");
		 formattedKeyVals.forEach((key,val) -> System.out.println(key +"=" + val) );
		
		
		
		//System.out.println(formattedKeyVals.size());
		
		String csvUrl="https://mbsdisclosure.fanniemae.com/PoolTalk2/securities.csv?cusip="+formattedKeyVals.get("CUSIP")+"&pooltrustno="+formattedKeyVals.get("Pool Number");
		
		List<String> recordsFromCsv = getRecordsFromCsv(csvUrl,csvFile);
		
		String[] split = recordsFromCsv.get(0).split("\\|",-1);
		Map <String,String> csvKeyVals= new HashMap<String,String>();
		
		if(split.length-1==fieldMapping.length){
			
			for(int i=0;i<fieldMapping.length;i++){
				csvKeyVals.put(fieldMapping[i],split[i+1]);
				
			}
			
		}
		
		System.out.println("********************CSV Key VALUES**********************");
		
		
		
		csvKeyVals.forEach((key,val) -> System.out.println(key +"=" + val) );
		
		//Arrays.asList(fieldMapping).forEach(key -> System.out.print(csvKeyVals.get(key)+"|"));
		//System.out.println();
		//System.out.println(recordsFromCsv.get(0));
		
		
		System.out.println("***********************COMPARE RESULTS*********************************");
		
		List<ComparedResults> results=new ArrayList<>();
		
		csvKeyVals.forEach((key,val) -> {
			
			
			ComparedResults res=new ComparedResults();
			results.add(res);
			
			if(formattedKeyVals.get(key)!=null){
				
				if(formattedKeyVals.get(key).equalsIgnoreCase(val)){
					
					System.out.println(key +"--"+ val + " Match ");
					
					res.setAttributeName(key);
					res.setCsvValue(val);
					res.setHtmlValue(formattedKeyVals.get(key));
					res.setMatchValue("MATCH");
					
					
				}
				else{
					
					System.out.println(key +"--"+ val + "----------------> No Match ");
					
					res.setAttributeName(key);
					res.setCsvValue(val);
					res.setHtmlValue(formattedKeyVals.get(key));
					res.setMatchValue("NOMATCH");
					
				}
			}
			else{
				
				System.out.println(key + " Key not found ");
				res.setAttributeName(key);
				res.setMatchValue("KeyNotFound");
			}
			System.out.println();
		});
		
		
		return results;
		
		
	}
	
	
	
	
	
	
	public  boolean  isNumeric(String str){
		
	              try{
		           Double.parseDouble(str);
	               return true;
	              }catch(Exception e){
	            	  return false;
	              }
	
	}
	
	
	private  List<String> getRecordsFromCsv(String url,CommonsMultipartFile file) throws Exception{
		
		 BufferedReader br=null;
		if(file !=null){
			
			 br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			
		
		}else{
		 URL csvUrl = new URL(url);
			
		 InputStream openStream = csvUrl.openStream();
		  br = new BufferedReader(new InputStreamReader(openStream));
		 
		}
		 
		
		 
		 
		 CSVReader reader = new CSVReader(br);
		 
		 System.out.println();

		 List<String> records =new ArrayList<String>();
		 
		reader.readAll().forEach(ele ->records.add(Arrays.asList(ele).get(0)));
		
		br.close();
		return records;
		
	}
	
	
}
