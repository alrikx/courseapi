package de.bythweb.spingbootstarter.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicService {
	private List<Topic> topics = new ArrayList<Topic>(
			Arrays.asList(new Topic("ABAP", "ABAP on HANA", "working with ABAP on HANA"),
					new Topic("JAVA", "Java on HANA", "working with ABAP on HANA"),
					new Topic("SAPUI5", "SAPUI5", "SAP UI5 Desc")));

	public List<Topic> getAllTopics() {
		return topics;
	}

	public Topic getTopic(String id) {
		return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}

	public void createTopic(Topic topic) {
		topics.add(topic);
	}

	public void updateTopic(String id, Topic topic) {
		for (int i = 0; i < topics.size(); i++) {
			Topic currentTopic = topics.get(i);
			if (currentTopic.getId().equals(id)) {
				topics.set(i, topic);
				return;
			}
		}
	}

	public void deleteTopic(String id) {
		topics.removeIf(t -> t.getId().equals(id));
	}

}
