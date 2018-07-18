import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;  

public class ParserApphtmltocsv{

//Method to get the actual author name 	
	public static String replace(String name, String replaceWords){
			
		String replacepreffix= name.replaceAll(replaceWords,"");
		String replacesuffix= replacepreffix.replaceAll("\\[]","");
		String actualName=replacesuffix.replaceAll("[\\()]","");		
				return(actualName);
			}

//Method to get the disicipline			
			public static String getDisicipline(String listingname){
				 String[]listings=listingname.split("/");
				 String Disicipline="";
					for(String listing:listings){
						if(listing.contains(".html")){
							//System.out.println(listing);
							Disicipline=listing.replaceAll(".html","");
							
							}
					}
					return(Disicipline);
			}
			public static String htmlParser(File name,FileWriter htmlcsv,String disiciplines,String diciplineCategories){
				String title="";
		String resourseurl="";
		String licenses="";
		String lisenceurl="";
		String editorauctualName="";
		String authorauctualName="";
		String refAut="";
		String reviewAncillaries="";
		String review="";
		String Final="";
		boolean writfile=false;
		String resultParser="";
try{
//Parsing the HTML
		Document doc = Jsoup.parse(name, "UTF-8", "http://example.com/");
		Elements authors = doc.select("div.rListingTableContentCell");
			for (int i=0 ;i <authors.size();i++){
				Element resourses = doc.select ("div.rListingTableContentCell ").get(i);	
				Elements resourse = resourses.select("a:eq(0)");
				Elements license = resourses.select("a:eq(1)");	
				Elements review_ancillaries= resourses.select("a:eq(2)");
				Elements review_ancillaries_both= resourses.select("a:eq(3)");
				
				title=resourse.text();
				//title.add(resourse.text());
				resourseurl=resourse.attr("href");
				//resourse_url.add(resourse.attr("href"));
				licenses=license.text();
				//licenses.add(license.text());
				lisenceurl=license.attr("href");
				//lisence_url.add(license.attr("href"));
				
				String authorFull=resourses.ownText();
			
				if(authorFull.contains("ed. by")){
					authorauctualName="";
					String replaceWord="ed. by";
					 editorauctualName=	replace(authorFull,replaceWord);
					//editors.add(editorauctualName);
				
				}
			else {
				editorauctualName="";
				String replaceWord="by";
				 authorauctualName=	replace(authorFull,replaceWord);
				//authornmae.add(authorauctualName);
				
			}
			review="";
			reviewAncillaries="";
			if((review_ancillaries.size() > 0 )&& (review_ancillaries != null)){
				
					 refAut=resourse.text();
				
					if((review_ancillaries.text()).equals("Review")){
						
						review=review_ancillaries.attr("href");
						//review_table.put(refAut, review_ancillaries.attr("href"));
					}else{
						//review_table.put(refAut, "");
						review="";
					}
					if((review_ancillaries.text()).equals("Ancillaries")){
						
						//ancillaries_table.put(refAut, review_ancillaries.attr("href"));
						reviewAncillaries=review_ancillaries.attr("href");
						
					}else{
						//ancillaries_table.put(refAut, "");
						reviewAncillaries="";
					}
					
					if((review_ancillaries_both.size() > 0 )&& (review_ancillaries_both != null)){
				
					 refAut=resourse.text();
				
					if((review_ancillaries_both.text()).equals("Review")){
						
						review=review_ancillaries_both.attr("href");
						//review_table.put(refAut, review_ancillaries.attr("href"));
					}
					if((review_ancillaries_both.text()).equals("Ancillaries")){
						
						//ancillaries_table.put(refAut, review_ancillaries.attr("href"));
						reviewAncillaries=review_ancillaries_both.attr("href");
						
					}
			
				}
		
				
				}
							
						
				 writfile= writetoFile(htmlcsv,title,resourseurl,licenses,lisenceurl,editorauctualName,authorauctualName,refAut,reviewAncillaries,review,disiciplines,diciplineCategories);
			
			
			}
			if(writfile){
				resultParser="CSV file was created successfully !!!";	
			}
			else{
			resultParser="Error in writing the file !!!";
			}
			//System.out.println(Final);
}catch (Exception e) {

            System.out.println("Error in Parsing !!!");

           // e.printStackTrace();

        }
		return resultParser;
			}
//Method to wrie to a csv File
		public static boolean writetoFile(FileWriter writer,String titles,String resourseurls,String license,String lisenceurls,String editorauctualNames,String authorauctualNames,String refAuts,String reviewAncillariess,String reviews,String disiciplines,String diciplineCategorys)
			{
				
				String review_url_value="";
				String ancillaries_url_value="";
	
				ArrayList<String> title = new ArrayList<String>();
				ArrayList<String> resourse_url = new ArrayList<String>();
				ArrayList<String> authornmae = new ArrayList<String>();
				ArrayList<String> editors = new ArrayList<String>();
				ArrayList<String> licenses = new ArrayList<String>();
				ArrayList<String> lisence_url = new ArrayList<String>();
				ArrayList<String> review_url = new ArrayList<String>();
				ArrayList<String> ancillaries_url = new ArrayList<String>();
				ArrayList<String> review_resourse_ref = new ArrayList<String>();
				ArrayList<String> ancillaries_resourse_ref = new ArrayList<String>();
				
		
				Hashtable<String, String> review_table = new Hashtable<String, String>();
				Hashtable <String, String> ancillaries_table = new Hashtable<String, String>();
				
		try{
				String COMMA_DELIMITER = ";";
				String NEW_LINE_SEPARATOR = "\n";
//Adding into Arraylist just for future but as of now not necessary 
				title.add(titles);
				resourse_url.add(resourseurls);
				licenses.add(license);
				lisence_url.add(lisenceurls);
				editors.add(editorauctualNames);
				authornmae.add(authorauctualNames);
				review_table.put(refAuts, reviews);
				ancillaries_table.put(refAuts, reviewAncillariess);
			
//Writing into CsvFile			
				writer.append(String.valueOf(titles));
				writer.append(COMMA_DELIMITER);
					
				writer.append(String.valueOf(resourseurls));
				writer.append(COMMA_DELIMITER);
					
				writer.append(String.valueOf(authorauctualNames));
				writer.append(COMMA_DELIMITER);
					
				writer.append(String.valueOf(editorauctualNames));
				writer.append(COMMA_DELIMITER);
						
				writer.append(String.valueOf(license));
				writer.append(COMMA_DELIMITER);

				writer.append(String.valueOf(lisenceurls));
				writer.append(COMMA_DELIMITER);
					
	//Future Needs				
				/*	 Enumeration e = review_table.keys();
					while (e.hasMoreElements()) {
						String auth_review_ref = (String)e.nextElement();
			  
						if((titles).equals(auth_review_ref)){
							
							review_url_value=(String)review_table.get(auth_review_ref);
							
							break;
						}else{
						review_url_value="";}
					}*/
					
				writer.append(String.valueOf(reviews));
				writer.append(COMMA_DELIMITER);
	
	//Future Needs						
				/* Enumeration anci = ancillaries_table.keys();
					while (anci.hasMoreElements()) {
						String auth_anci_ref = (String)anci.nextElement();
			  
						if((titles).equals(auth_anci_ref)){
							
							ancillaries_url_value=(String)ancillaries_table.get(auth_anci_ref);
							
							break;
						}else{
						ancillaries_url_value="";}
							
					}*/
					
				writer.append(String.valueOf(reviewAncillariess));
				writer.append(COMMA_DELIMITER);
			
				writer.append(String.valueOf(disiciplines));
				writer.append(COMMA_DELIMITER);
				

				writer.append(String.valueOf(diciplineCategorys));
				writer.append(COMMA_DELIMITER);
						 
				writer.append(NEW_LINE_SEPARATOR);
			
		}catch (Exception e) {

            System.out.println("Error in CsvFileReader !!!");

           // e.printStackTrace();

        }
			return true;

	}	
	
//Method to get the diciplineCategory	
		public static String getDisiciplinecategory(String disiciplinename){
										 
					String disciplinecatergory="";
					 if(disiciplinename.equals("education")||disiciplinename.equals("english&composition")||disiciplinename.equals("finearts")||disiciplinename.equals("history")||disiciplinename.equals("language&communication")||disiciplinename.equals("literature")||disiciplinename.equals("philosophy")){
					 disciplinecatergory="Humanities";		

					}
				  if(disiciplinename.equals("computer&intormationscience")||disiciplinename.equals("engineering&electronics")||disiciplinename.equals("health&nursing")){
					 disciplinecatergory="Applied_science";		
 
					}
				  if(disiciplinename.equals("accounting&finance")||disiciplinename.equals("generalbusiness")){
					 disciplinecatergory="Business";		

					}
				 if(disiciplinename.equals("biology&genetics")||disiciplinename.equals("chemistry")||disiciplinename.equals("generalscience")||disiciplinename.equals("mathematics")||disiciplinename.equals("physics")||disiciplinename.equals("statistics&probability")){
					 disciplinecatergory="Natural_science";		
 
					}
				  if(disiciplinename.equals("anthroplogy&archaeology")||disiciplinename.equals("economics")||disiciplinename.equals("law")||disiciplinename.equals("politicalscience")||disiciplinename.equals("psychology")||disiciplinename.equals("sociology")){
					 disciplinecatergory="Social_science";		

					}
				 	return(disciplinecatergory);
			}
			
	
public static void main(String[] args){
try{
FileWriter writers = null;
 //Feeding the  File input
		Scanner sc=new Scanner(System.in); 
		System.out.println("Enter your file to be parsed");  
   String inputListing=sc.next(); 
    System.out.println("Enter the path for storing the output");  
   String folderName=sc.next();  
   
		
		 File input = new File(inputListing);
		 System.out.println("The file can be read: " + input.canRead());

 //Method to get the disicipline
		String disicipline=getDisicipline(inputListing);
		
		
//Method to get the disciplinecatergory
		 //System.out.println("disciplinecatergory");	
		String diciplineCategory= getDisiciplinecategory(disicipline);
	
		
//	Writing Csv file	
		String NEW_LINE_SEPARATOR = "\n";
		writers = new FileWriter(folderName+disicipline+".csv");
		String FILE_HEADER = "title;resourse_ur;,author;editors;license;lisence_url;review_url;ancillaries_url;Discipline;Discipline-Category;";
		writers.append(FILE_HEADER.toString());
		writers.append(NEW_LINE_SEPARATOR);
String result =htmlParser(input,writers,disicipline,diciplineCategory);
System.out.println(result);
		
			 writers.flush();
				writers.close();
				sc.close();
			
	
 }
	 catch (Exception e){
		 System.out.println("coundnt get document");
		



}
}
}
