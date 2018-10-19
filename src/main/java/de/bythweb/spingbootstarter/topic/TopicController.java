package de.bythweb.spingbootstarter.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;

	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}

	@RequestMapping("/topics/{id}")
	public Topic getTopic(@PathVariable String id) {
		return topicService.getTopic(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/topics")
	public void createTopic(@RequestBody Topic topic) {
		topicService.createTopic(topic);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/topic/{id}")
	public void updateTopic(@PathVariable String id, @RequestBody Topic topic) {
		topicService.updateTopic(id, topic);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/topic/{id}")
	public void deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
	}
}
