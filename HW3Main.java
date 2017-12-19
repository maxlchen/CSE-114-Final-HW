//Name: Max Chen
//Student ID: 111316366
//Stony Brook University
//CSE 114
//HW 3	
package hw3;
import java.util.*;
import java.io.*;

public class HW3Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter input filename (note that this file must exist): ");
		String file = scan.nextLine();
		System.out.println("Enter output filename (note that this file must exist): ");
		String outfile = scan.nextLine();
		scan.close();
		File f = new File(file);
		File f2 = new File(outfile);		
		String output = "";
		output += "<!DOCTYPE html>\n<html>\n<head>\n<title>Results of Markdown Translation</title>\n</head>\n<body>\n<p>\n";
		try{
		Scanner readfile = new Scanner(f);
		Scanner scan2 = new Scanner(f2);
		FileWriter fw =  new FileWriter(f2);
		PrintWriter pw = new PrintWriter(fw);
		
		while(readfile.hasNextLine()){
			
			String text = readfile.nextLine();
			
//			If there are two blank lines per paragraph tag, then use the following code instead.
//			if(text.length() == 0){
//				text = readfile.nextLine();
//				if(text.length() == 0)
//					output += "<p>\n";
//			}
			if(text.length() == 0){
				output += "</p>\n<p>\n";
			}
		
			//if(!isEmphasis(text).equals("emphasis") && !isEmphasis(text).equals("strong emphasis") && !isHyperlink(text) && !isImage(text) && !isImage(text) && !isCode(text) && !isListItem(text)){
				//System.out.println();
			//}
			if(isEmphasis(text).equals("emphasis")){
				text = translateEmphasis(text);
			}
			if(isEmphasis(text).equals("strong emphasis")){
				text = translateStrongEmphasis(text);
			}
			if(isImage(text)){
				text = translateImage(text);
			}
			if(isHyperlink(text)){
				text = translateHyperlink(text);
			}
			
			if(isCode(text)){
				text = translateCode(text);
				
			}
			if(isListItem(text)){
				text = translateListItem(text);
			}
			output += text;
			//pw.print(output+ "\n");
		}
		if(output.indexOf("<li>") != -1){
			int index = output.indexOf("<li>");
			String temp = output.substring(0, index);
			String temp2 = output.substring(index);
			output = temp + "<ul>\n" + temp2;
			if(output.lastIndexOf("</li>") != -1){
				int index2 = output.lastIndexOf("</li>") + 5;
				String temp3 = output.substring(0, index2);
				String temp4 = output.substring(index2 + 1);
				output = temp3 + "\n</ul>\n" + temp4;
			}
			else{
				output = output + "</ul>\n";
			}
		}
		readfile.close();		
		output += "\n</p>\n\n</body>\n</html>";
		pw.print(output);
		pw.close();
		fw.close();
		scan2.close();
		System.out.println(output);

		}
		
		catch(FileNotFoundException FNFE){
			System.out.println("Error: File Not Found");			
		}
		catch(IOException IOE){
			
		}
		

	}
	
	public static String translateEmphasis(String text){
			String temp1 = text.substring(0, text.indexOf('*'));
			String temp2 = text.substring(text.indexOf('*') + 1);
			text = temp1 + "<em>" + temp2;
			if(text.indexOf('*')!= -1){
			temp1 = text.substring(0, text.indexOf('*'));
			temp2 = text.substring(text.indexOf('*') + 1);
			text = temp1 + "</em>" + temp2 + "\n";
			}
			else{
				text = temp1 + temp2;
			}
			
			return text;
		
	}
	public static String translateStrongEmphasis(String text){
		String temp1 = text.substring(0, text.indexOf('*'));
		String temp2 = text.substring(text.indexOf('*') + 2);
		text = temp1 + "<strong>" + temp2;
		if(text.indexOf('*') != -1){
		temp1 = text.substring(0, text.indexOf('*'));
		temp2 = text.substring(text.indexOf('*') + 2);
		text = temp1 + "</strong>" + temp2 + "\n";
		}
		else{
			text = temp1 + temp2;
		}
		return text;
		
	}
	
	public static String translateHyperlink(String text){
		int index1 = text.indexOf('[');		
		int index4 = text.indexOf(']');
		String linkAndFurther = text.substring(index4 + 1);
		int index2 = linkAndFurther.indexOf('(');
		int index3 = linkAndFurther.indexOf(')');
		int index5 = text.lastIndexOf(')');
		text = text.substring(0, index1) + "<a href = \"" + linkAndFurther.substring(index2 + 1, index3) + "\">" + text.substring(index1 + 1, index4) + "</a>" + text.substring(index5 + 1) + "\n";
		return text;
	}
	
	public static String translateImage(String text){
		int index1 = text.indexOf('[');
		int index2 = text.indexOf('(');
		int index3 = text.indexOf(')');
		int index4 = text.indexOf(']');
		//int index0 = text.indexOf('!');
		text = text.substring(0, index1 - 1) + "<img src = \"" + text.substring(index2 + 1, index3) + "\" alt=\"" + text.substring(index1 + 1, index4) + "\" title = " + text.substring(text.indexOf('"'), index3)+ ">" + text.substring(index3 + 1) + "\n";
		return text;
		
	}

	public static String translateCode(String text){
		int index1 = text.indexOf('`');
		String temp = text.substring(0, index1);
		String temp2 = text.substring(index1 + 1);
		text = temp + "<code>" + temp2;
		int index2 = text.indexOf('`');
		temp = text.substring(0, index2);
		temp2 = text.substring(index2 + 1);
		text = temp + "</code>" + temp2 + "\n";
		
		return text;
	}
	
	public static String translateListItem(String text){
		int index = text.indexOf("+");
		String temp = text.substring(index + 1);
		text = "<li>" + temp + "</li>" + "\n";
		return text;
		
	}
	
	public static boolean isParagraph1(String firstline){
		if(firstline.equals(""))
			return true;
		else
			return false;
		
	}
	
	
	public static boolean isParagraph2(String secondline){
		if(secondline.equals(""))
			return true;
		else
			return false;
		
	}
	
	public static String isEmphasis(String text){
		//After calling this method, if there is an emphasis, will need to create a substring and call again to check for more emphasis.
		if(text.indexOf('*')!= -1){
			//if there is an asterisk, create a substring to look for the rest of the asterisks
			String rest = text.substring(text.indexOf('*') + 1);
			//checking if strong
			if(rest.charAt(0) == '*'){
				//if(rest.substring(1).indexOf('*')== -1){
					//return "emphasis";					
				//}
				//if there are more asterisks, then it is a strong emphasis
				//else{
					return "strong emphasis";
				//}
				
			}
			else{
				return "emphasis";
			}
		}
		else{
			return "no emphasis";
		}
	}
	
	public static boolean isHyperlink(String text){
		if(text.indexOf('[') != -1){
			int index1 = text.indexOf('[');
			String rest1 = text.substring(index1 + 1);
			if(rest1.indexOf(']') != -1){
				int index2 = rest1.indexOf(']');
				String rest2 = rest1.substring(index2 + 1);
				if(rest2.charAt(0) == '('){
					int index3 = rest2.indexOf('(');
					String rest3 = rest2.substring(index3 + 1);
					if(rest3.indexOf(')') != -1){
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
				
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public static boolean isImage(String text){
		if(text.indexOf('!') != -1){
			int index0 = text.indexOf('!');
			String rest0 = text.substring(index0+1);
			if(rest0.charAt(0) == '['){
				int index1 = text.indexOf('[');
				String rest1 = text.substring(index1 + 1);
				if(rest1.indexOf(']') != -1){
					int index2 = rest1.indexOf(']');
					String rest2 = rest1.substring(index2 + 1);
					if(rest2.charAt(0) == '('){
						int index3 = rest2.indexOf('(');
						String rest3 = rest2.substring(index3 + 1);
						if(rest3.indexOf(')') != -1){
							return true;
						}
						else{
							return false;
						}
					}
					else{
						return false;
					}
					
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
			
		}
		else{
			return false;
		}
	}

	public static boolean isCode(String text){
		if(text.indexOf('`') != -1){
			int index = text.indexOf('`');
			String rest = text.substring(index + 1);
			if(rest.indexOf('`') != -1){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	public static boolean isListItem(String text){
		if(!text.substring(0).equals("")){
		if(text.charAt(0) == '+'){
			return true;
		}
		else{
			return false;
		}
		}
		else{
			return false;
		}
	}
	
	
}
