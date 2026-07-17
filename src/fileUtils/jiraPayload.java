package fileUtils;

public class jiraPayload {

	public static String getCreateIssuePayload() {
		String createIssuePayload = "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SAI\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Order Submit Button not working as expected\",\r\n"
				+ "       \"description\": {\r\n"
				+ "        \"type\" : \"doc\",\r\n"
				+ "        \"version\" : 1,\r\n"
				+ "        \"content\": [\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"paragraph\",\r\n"
				+ "                \"content\":[\r\n"
				+ "                    {\r\n"
				+ "                        \"type\": \"text\",\r\n"
				+ "                        \"text\": \"Submit Button not working\"\r\n"
				+ "                    }\r\n"
				+ "                    \r\n"
				+ "\r\n"
				+ "                ]\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "       },\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}";
		return createIssuePayload;
	}
	
	
}
