//Julien Kastelic 11/15/2019
//This program reads a text file and performs three functions: 
//1) give a total word count
//2) give a list of the top 10 used words sorted 
//3) gives the sentence that last used the most used word.

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class FileFunctions {
	
	//hold the file being used in this program
	public static File file;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//read in the text file
	    //file = new File("C:\\Users\\Julien\\Desktop\\passage.txt"); 
	    file = new File("src/files/passage.txt"); 

        //get word count
        findAndPrintWordCount();
        
        //get top 10 used words, this method calls the last sentence method.
        findAndPrintTop10();
        
	}
	
	//this method uses the buffered reader to get a total word count of the text file
	public static void findAndPrintWordCount() throws IOException {
		
		 try {
			 BufferedReader reader = new BufferedReader(new FileReader(file));
			//begin reading file lines
				
				String line = reader.readLine();
				
			    int wordCount = 0;
			     
			    while (line != null) {
			    	
			    	  //split the read line, delimit by regex for whole words
			          String []words = line.split("\\W+");
			          
			          //loop through line to count words     
			          for (String word : words){
			        	  wordCount++;        
			          }
			          
			          //read next line
			          line = reader.readLine();
			    }
			    
			    //close reader for this method
			    reader.close();
			    
			    //print word count for the file
			    System.out.println("total word count: " + wordCount);
			    System.out.println("-------------------------------");
			    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	}
	
	
	//this method takes the text file and reports the top 10 frequently used words and then calls the method to find the last sentence which contains the most used word
	public static void findAndPrintTop10() throws IOException {
		
		 try {
		   
			 BufferedReader reader = new BufferedReader(new FileReader(file));
			
			//use hash map to store a word-word frequency key-pair
			 HashMap<String, Integer> wordFreqMap = new HashMap<String,Integer>();
			 
			 String line = reader.readLine();
			
		   
		    //loop through each line of words, either adding them to the hash map or updating their value
		    while (line != null) {
		    	  //delimit by regex for whole word characters
		          String []words = line.split("\\W+");
		          
		          //to make a consistent word frequency, ignore case for each line being read
		          for (int i = 0; i < words.length; i++){
		        	  words[i].toLowerCase();        
		          }
		          
		          //loop through line to count words
		          for (String word : words){
		        	  //if the map has this word, add to its count
		        	  if(wordFreqMap.containsKey(word)) {
		        		  wordFreqMap.put(word, wordFreqMap.get(word) +1);
		        	  }
		        	  //word doesn't exist in map yet, add it
		        	  else {
		        		  wordFreqMap.put(word, 1);
		        	  }
		          }
		          //read next line
		          line = reader.readLine();
		    }
		    
		    //now sort hash map to get top 10 used words
		    		     
		    //convert the hash map into a list for using comparator to sort the list into descending order
		    List<Map.Entry<String, Integer>> wordFreqList = new LinkedList<Map.Entry<String, Integer>>(wordFreqMap.entrySet());
		    
		    Collections.sort(wordFreqList, new Comparator<Map.Entry<String, Integer>>() {
	            //reverse sort to get list descending
		    	public int compare(Map.Entry<String, Integer> o2,
	                               Map.Entry<String, Integer> o1) {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });
		    
		    //get the top 10 results from the sorted list descending:
		    List<Entry<String, Integer>> wordList = wordFreqList.subList(0, 10);
		    
		    System.out.println("Top 10 used words in the passage: ");
		    
		    //loop the shortened list
		    for(int i =0; i< wordList.size(); i++) {
		    	System.out.println(wordFreqList.get(i));
		    }
		    
		    System.out.println("-------------------------------");
		    
		    //now get the last sentence that used the most used word
		    
		    //get most used word from the top 10 list:
		    String mostUsedWord = wordList.get(0).toString();
		    //cut hash map portions of the string
		    mostUsedWord = mostUsedWord.substring(0, mostUsedWord.indexOf("="));
		    
		    //close buffered reader for this method
		    reader.close();
		    
		    //call the sentence finding method
		    findLastSentenceWithMostUsedWord(mostUsedWord);
		    
		  
		    
		 } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	     } 
			
		
	}
	
	//this method splits the file into sentences and then finds the last sentence which contains the most used word
	public static void findLastSentenceWithMostUsedWord(String mostUsedWord) throws IOException {
		
		
		//read the whole file into a string and then split into sentences
		String contents = new String(Files.readAllBytes(Paths.get("src/files/passage.txt")));
		
		//split file contents by period marking the end of each sentence.
		String[] sentences = contents.split("\\.");
		
		System.out.println("Last sentence which uses the most used word \"" + mostUsedWord + "\":");
		
		//loop backwards in the list of sentences and detect first use of the most used word and then break loop.
		for(int i=sentences.length-1; i>0; i--) {
			
			//detect if the sentence contains the most used word 
			if(sentences[i].contains(mostUsedWord)){
				//sentence found, print and break the loop
				System.out.println(sentences[i]);
				//break loop
				i=0;
			}
		}

			
		
			
	}

}
