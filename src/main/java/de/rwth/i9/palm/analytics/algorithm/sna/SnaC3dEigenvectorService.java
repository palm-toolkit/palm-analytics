package de.rwth.i9.palm.analytics.algorithm.sna;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.mahout.cf.taste.common.TasteException;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.EigenvectorCentrality;
import org.gephi.statistics.plugin.GraphDistance;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openide.util.Lookup;
import org.springframework.stereotype.Service;

/**
 * Master Thesis at The Learning Technologies Research Group (LuFG Informatik 9,
 * RWTH Aachen University), Year 2014 - 2015
 * 
 * The implementation of SNA-based RS with 3-depth co-authorship network and
 * Eigenvector
 * 
 * @author Peyman Toreini
 * @version 1.1
 */
@Service
public class SnaC3dEigenvectorService {
	static ArrayList<String> EigenvectorNames = new ArrayList<String>();

	/**
	 * @param researcherID
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 * @throws IOException
	 * @throws TasteException
	 */
	public static JSONArray computeEigenvector(int researcherID)
			throws JSONException, SQLException, IOException, TasteException {
		Map<Integer, Double> EigenvectorResult = new TreeMap<Integer, Double>();
		// Initiate a project - and therefore a workspace
		ProjectController pc = Lookup.getDefault().lookup(
				ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();
		// Get graph model and attribute model of current workspace
		GraphModel graphModel = Lookup.getDefault()
				.lookup(GraphController.class).getModel();
		AttributeModel attributeModel = Lookup.getDefault()
				.lookup(AttributeController.class).getModel();
		ImportController importController = Lookup.getDefault().lookup(
				ImportController.class);
		// Import file
		Container container;
		try {
			File file = new File("data/SNA3dfile.csv");
			container = importController.importFile(file);
			container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED); // Force
																			// UNDIRECTED
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		// Append imported data to GraphAPI
		importController.process(container, new DefaultProcessor(), workspace);
		UndirectedGraph graph = graphModel.getUndirectedGraph();
		// *********start Eigenvector
		GraphDistance graphDistance = new GraphDistance();
		graphDistance.setDirected(false);
		graphDistance.setNormalized(true);
		graphDistance.execute(graphModel, attributeModel);
		EigenvectorCentrality eigen = new EigenvectorCentrality();
		eigen.execute(graphModel, attributeModel);
		AttributeColumn Clos = attributeModel.getNodeTable().getColumn(
				EigenvectorCentrality.EIGENVECTOR);
		for (Node n : graph.getNodes()) {
			Double EigenResult = (Double) n.getNodeData().getAttributes()
					.getValue(Clos.getIndex());
			int rid = Integer.parseInt(graph.getNode(n.getId()).toString());
			// create ID and Eigenvector
			EigenvectorResult.put(rid, EigenResult);
		}
		Map<Integer, Double> sortedMap2 = UtilService
				.sortByComparator(EigenvectorResult);
		ArrayList<Integer> CoAuthIds = UtilService.getCoAuthorIDs(researcherID);
		// print sorted Map
		Map<Integer, Double> rIDEigenvector = new LinkedHashMap<Integer, Double>();
		for (Entry<Integer, Double> entry : sortedMap2.entrySet()) {
			if (CoAuthIds.contains(entry.getKey())) {
				// do nothing
			} else if (entry.getKey() != researcherID) {
				rIDEigenvector.put(entry.getKey(), entry.getValue());
			}
		}
		// Compute importance of all nodes in compare with searched node
		Map<Integer, Double> SortedBaseImportanceLevel = UtilService
				.ComputeImportance(rIDEigenvector, researcherID);
		ArrayList<Integer> rIDs = new ArrayList<Integer>();
		Map<Integer, Double> rIDEigenvectorFinal = new LinkedHashMap<Integer, Double>();
		int i = 0;
		for (Entry<Integer, Double> entry : SortedBaseImportanceLevel
				.entrySet()) {
			if (i < 10) {
				rIDs.add(entry.getKey());
				rIDEigenvectorFinal.put(entry.getKey(),
						sortedMap2.get(entry.getKey()));
			}
			i++;
		}
		Map<Integer, String> rIDName2 = UtilService.getName(rIDs);
		EigenvectorNames.clear();
		for (Entry<Integer, String> entry : rIDName2.entrySet()) {
			EigenvectorNames.add(entry.getValue());
		}
		JSONArray EigenvectorRecResult = UtilService.EigenvectorJsonCreator(
				rIDEigenvectorFinal, rIDName2, researcherID);
		return EigenvectorRecResult;
	}

	/**
	 * @param researcherID
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws SQLException
	 */
	private JSONArray topRatedItemsEigenvector(int researcherID)
			throws JSONException, IOException, SQLException {
		String namesforQuery = UtilService.namesListForQuery(EigenvectorNames);
		// Find interests of the user
		ArrayList<String> iListof1User = new ArrayList<String>();
		iListof1User = UtilService.getInterestOf1User(researcherID);
		String idsforQuery = UtilService.idsListForQuery(namesforQuery);
		JSONArray topRatedItems = new JSONArray();
		/*
		 * Map<Integer, String> palmResearchers = ResearcherService
		 * .allRMapCreator();
		 */
		Map<String, Integer> interestIDTableMap = UtilService
				.interestIDTableMap();
		// 1.get the connection to db
		Connection myConn;
		try {
			myConn = DbService.ConnectToDB();
			// 2.create statement
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt
					.executeQuery("select DISTINCT authorid,result,score from results WHERE authorid IN ("
							+ idsforQuery + ")ORDER BY score DESC;");
			// check not to recommend one Item 2 times.
			ArrayList<String> checkedList = new ArrayList<String>();
			int i = 0;
			while (myRs.next()) {
				if (iListof1User.contains(myRs.getString("result"))
						|| checkedList.contains(myRs.getString("result"))) {
				} else if (i < 10) {
					checkedList.add(myRs.getString("result"));
					JSONObject obj = new JSONObject();
					obj.put("AuthorID", myRs.getInt("authorid"));
					/*
					 * obj.put("AuthorName",
					 * ResearcherService.findOneResearcher( palmResearchers,
					 * myRs.getInt("authorid")));
					 */
					obj.put("itemName", myRs.getString("result"));
					obj.put("InterestID",
							UtilService.get1InterestID(interestIDTableMap,
									myRs.getString("result")));
					obj.put("Score", myRs.getString("score"));
					topRatedItems.put(obj);
					i++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topRatedItems;
	}

	/**
	 * Final Step - sending file to control
	 * 
	 * @param selectedRId
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws TasteException
	 * @throws SQLException
	 */
	public String snaEigenvectorRecommender(int selectedRId)
			throws JSONException, IOException, TasteException, SQLException {
		JSONArray EigenvectorRecResult = new JSONArray();
		int researcherID = selectedRId;
		// Create SNA file
		UtilC3d.SNA3dfileCreator(researcherID);
		// Recommend collaborators based on Eigenvector and Jaccard Similarity
		JSONArray EigenvectorSimillarR = computeEigenvector(researcherID);
		JSONObject EigenvectorSimillarRObj = new JSONObject();
		EigenvectorSimillarRObj.put("sResearchers", EigenvectorSimillarR);
		// Find top rated Items of Recommended users
		JSONArray topRatedItems = topRatedItemsEigenvector(researcherID);
		JSONObject EigenvectorTopItemsObj = new JSONObject();
		EigenvectorTopItemsObj.put("rItems", topRatedItems);
		EigenvectorRecResult.put(EigenvectorSimillarRObj);
		EigenvectorRecResult.put(EigenvectorTopItemsObj);
		// Paper recommendation based recommended researchers
		ArrayList<Integer> rIDs = UtilService
				.FindRIdsInFinalJson(EigenvectorRecResult);
		ArrayList<String> iNames = UtilService
				.FindINamesInFinalJson(EigenvectorRecResult);
		JSONArray EigenvectorRecFinalResult = UtilService.addPubsOfIToJson(
				EigenvectorRecResult, rIDs, iNames);
		// start Common interest Json
		JSONArray EigenvectorRecFinalResult2 = UtilService
				.addCommonInterestListToJson(EigenvectorRecFinalResult, rIDs,
						researcherID);
		return EigenvectorRecFinalResult2.toString();
	}
}