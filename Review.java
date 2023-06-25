import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class that contains helper methods for the Review Lab
 * (method removePunctuation() was added from teacher code)
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        //System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
  /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

/** Activity 2: totalSentiment()
  * Write the code to total up the sentimentVals of each word in a review.
 */
  public static double totalSentiment(String filename)
  {
    // read in the file contents into a string using the textToString method with the filename
    String rev = Review.textToString(filename);

    // set up a sentimentTotal variable
    double sentimentTotal = 0;

    // loop through the file contents
    int x = 0;
    int y = 0;
    while (x<rev.length())
    {
      y = rev.indexOf(" ", x);
      if(y == -1)
      {
        sentimentTotal += sentimentVal(rev.substring(x));
        break;
      }
      String word = rev.substring(x, y);
      x = y+1;
      y = 0;
      word = removePunctuation(word);
      word = word.toLowerCase();
      sentimentTotal += sentimentVal(word);
    }
    return sentimentTotal;
  }
  public static String fakeReview(String fileName)
  {
    String origReview = textToString(fileName);
    String fakeReview = "";

    for(int c = 0; c < origReview.length()-1; c++)
    {
      if(origReview.substring(c, c+1).equals("*"))
      {
        c++;
        String change = "";
        boolean rBoolVal = true;
        while( rBoolVal )
        {
          change = change + origReview.substring(c, c+1);
          c++;
          if(origReview.substring(c, c+1).equals(" "))
          {
            rBoolVal = false;
          }
        }
        change = change.replace('*', ' ');
        change = randomAdjective() + " ";
        fakeReview = fakeReview + change;
      }
      else
      {
        fakeReview = fakeReview + origReview.substring(c, c+1);
      }
    }
    return fakeReview;
  }

  public static String enhancedFakeReview(String fileName)
  {
    String origReview = textToString(fileName);
    String fakeReview = "";

    for(int c = 0; c < origReview.length()-1; c++)
    {
      if(origReview.substring(c, c+1).equals("*"))
      {
        c++;
        String change = "";
        boolean rBoolVal = true;
        while(rBoolVal)
        {
          change += origReview.substring(c, c+1);
          c++;
          if(origReview.substring(c, c+1).equals(" "))
          {
            rBoolVal = false;
          }
        }
        change = change.replace('*', ' ');
        if(sentimentVal(change) > 0)
        {
          String word = change;
          change = randomPositiveAdj();
          while(sentimentVal(change) < sentimentVal(word))
          {
            change = randomPositiveAdj();
          }
          change += " ";
        }
        else if (sentimentVal(change) < 0)
        {
          String word = change;
          change = randomNegativeAdj();
          while(sentimentVal(change) > sentimentVal(word))
          {
            change = randomNegativeAdj();
          }
          change += " ";
        }
        else
        {
          change = randomAdjective() + " ";
        }
        fakeReview += change;
      }
      else
      {
        fakeReview += origReview.substring(c, c+1);
      }
    }
    return fakeReview;
  }

  /** Activity 2 starRating method
     Write the starRating method here which returns the number of stars for the review based on its totalSentiment.
  */
  public static int starRating(String filename)
  {
    // call the totalSentiment method with the fileName
    double stars = Review.totalSentiment(filename);

    // determine number of stars between 0 and 4 based on totalSentiment value 
    int starRating; // change this!
    // write if statements here
    if( stars > 15)
    {
      starRating = 5;
    }
    else if( stars > 10 && stars < 15 )
    {
      starRating = 4;
    }
    else if( stars > 5 && stars < 10)
    {
      starRating = 3;
    }
    else if( stars > 0 && stars <5 )
    {
      starRating = 2;
    }
    else
      starRating = 1;
    // return number of stars
    return starRating;
  }
}
