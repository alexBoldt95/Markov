import java.util.*;

public class MapMarkovModel extends MarkovModel {
	protected Map<String, ArrayList<String>> markovMap;
	protected String myStringCopy;
	protected int prevOrder;
	//myStringBackUp will be used to see if myString is currently
	//equivalent to it's old value, i.e. the training text file has not been changed since the last run.
	//Markov map is the kGram map associated with a given training file and order k.
	//prevOrder will become the current order k if it has been changed
	
	public void makeMap(int k){
		//Creates a HashMap to be filled with key kGrams and value lists of following kGrams
		markovMap = new HashMap<String, ArrayList<String>>();
		int pos = 0;
		boolean end = false;   //Used to check is the end of the file has been reached in the subsequent while loop
		
		while(pos <= myString.length() && !end){
			//firstKgram is a substring from position to position+k
			String firstGram = myString.substring(pos, pos + k); 
			String nextGram;
			//The next, corresponding kGram can either be the end of file or a new kGram.
			if(pos + k + 1 <= myString.length()){
				nextGram = myString.substring(pos+1, pos + k+1);
			}
			else{
				nextGram = "**ENDFILE**";  //Maps the end of the file
				end = true;
			}
			pos++;
			//Maps the first Kgram to a list of the following kGrams.
			ArrayList<String> listNextGrams = markovMap.get(firstGram);
			if(listNextGrams == null){
				markovMap.put(firstGram, new ArrayList<String>());
			}
			markovMap.get(firstGram).add(nextGram);
		}
	 }
//		Code for testing the map, looks good 
//		for(String key:markovMap.keySet()){
//			System.out.print(key);
//			System.out.print(markovMap.get(key));
//			System.out.println(markovMap.get(key).size());			
//		}
	protected String makeNGram(int k, int maxLetters){
		
		//Creates the map if it doesn't already exist or if the order has been changed or if the training
		//file has been changed or if the order has been changed.
		if(markovMap==null || !myStringCopy.equals(myString) || prevOrder!=k){
			makeMap(k);
			myStringCopy = new String(myString);
			prevOrder = k;
		}
		
		//beginning the building of the Markov chain
		StringBuilder build = new StringBuilder();
		int start = myRandom.nextInt(myString.length() - k + 1);
        String seed = myString.substring(start, start + k);
        build.append(seed);
        // generate at most maxLetters characters
        for (int i = 0; i < maxLetters; i++) {
        	ArrayList<String> possGrams = markovMap.get(seed);
            int pick = myRandom.nextInt(possGrams.size()); //randomly picks a next kGram from the list of kGrams mapped to that kGram
            String nextGram = possGrams.get(pick);
            if ("**ENDFILE**".equals(nextGram)) //end-of-file
            	return build.toString();
            //take the last character of the chosen gram to add to the build string
            char ch = nextGram.charAt(nextGram.length()-1); 
            build.append(ch);
            seed = nextGram;
        }
        return build.toString();
	}
	
	
	
}


