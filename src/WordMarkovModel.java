import java.util.*;

public class WordMarkovModel extends MapMarkovModel {	
	protected Map<WordNgram, ArrayList<WordNgram>> markovMap;
	protected String[] words;
	//Markov map is the kGram map associated with a given training file and order k.
	
	public void makeMap(int k){
		//Creates a HashMap to be filled with key kGrams and value lists of following kGrams
		words = myString.split("\\s+");  //The array of words that make up the file
		markovMap = new HashMap<WordNgram, ArrayList<WordNgram>>();
		int pos = 0;
		boolean end = false;   //Used to check is the end of the file has been reached in the subsequent while loop
		
		while(pos <= words.length && !end){
			//firstgram is a substring from position to position+k
			WordNgram firstGram = new WordNgram(words, pos, k); 
			WordNgram nextGram;
			//The next, corresponding kGram can either be the end of file or a new kGram.
			if(pos + k + 1 <= words.length){
				nextGram = new WordNgram(words, pos+1, k);
			}
			else{
				nextGram = null;  //Maps the end of the file
				end = true;
			}
			pos++;
			//Maps the first kGram to a list of the following kGrams.
			ArrayList<WordNgram> listNextGrams = markovMap.get(firstGram); 
			if(listNextGrams == null){
				markovMap.put(firstGram, new ArrayList<WordNgram>());
			}
			markovMap.get(firstGram).add(nextGram);
		}
		//Used to print the size of the keySet, i.e., the amount of WordNgrams generated for a given k.
		//System.out.println(markovMap.keySet().size());
//      Used for testing the map, seems good.
//		for(WordNgram key:markovMap.keySet()){
//			System.out.print(key.toString());
//			System.out.print(markovMap.get(key));
//			System.out.println(markovMap.get(key).size());			
	
	}
		
	protected String makeNGram(int k, int maxLetters) {
		//Creates the map if it doesn't already exist or if the order has been changed or if the training
		//file has been changed.
		if(markovMap==null || !myStringCopy.equals(myString) || prevOrder!=k){
			makeMap(k);
			myStringCopy = new String(myString);
			prevOrder = k;
		}
		//beginning the building of the Markov chain
		StringBuilder build = new StringBuilder();
		int start = myRandom.nextInt(words.length - k + 1);
        WordNgram seed = new WordNgram(words, start, k);
        
        // generate at most maxLetters characters
        for (int i = 0; i < maxLetters; i++) {
        	ArrayList<WordNgram> possGrams = markovMap.get(seed);
        	//randomly picks a next kGram from the list of kGrams mapped to that kGram
        	int pick = myRandom.nextInt(possGrams.size()); 
            WordNgram nextGram = possGrams.get(pick);
            if (nextGram == null) //end-of-file
            	return build.toString();
            String word = nextGram.getLastWord(); //take the last word of the chosen gram to add to the build string
            build.append(word);
            build.append(" ");
            seed = nextGram;
        }
        build.deleteCharAt(build.length()-1); 
        return build.toString();
	}
}
