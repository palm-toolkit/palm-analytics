package de.rwth.i9.palm.analytics.algorithm.dynamicLDA;



public class termDocmatrix
{	
	public int[][] termdoc;
	
	public termDocmatrix (){
		
	}
	
	
	// get the number of topics by using mallet ( getTopic number)
	// get the length of documents by using mallet -> DocumentLengths
	// size of vocabulary by mallet -> getAlphabet.size()
	// number of documents InstanceList.data.size()
	// get the tokens for each of the documents data.get(docID).instance.getData()
		// if needed the length of document is data.get(docID).instance.getData().size()
	// use termDocmatrix to get the term counts for each of the documents

	public void creatematrix()
	{
		dynamicLDA d = new dynamicLDA();
		cc.mallet.topics.ParallelTopicModel model = d.createModel( "C:/Users/Piro/Desktop/", "Years", 10, 5 );
		System.out.println(model.getAlphabet());
	}
}
