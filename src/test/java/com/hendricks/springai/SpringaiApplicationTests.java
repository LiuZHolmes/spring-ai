package com.hendricks.springai;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.AllArgsConstructor;

@SpringBootTest
@AllArgsConstructor
class SpringaiApplicationTests {

	// private final ChatClient.Builder builder;

	@Test
	void contextLoads() {
	}

	// @Test
	// void testEvaluation() {

	// 	String userText = "What is the purpose of Carina?";

	// 	ChatResponse response = builder
	// 			.build()
	// 			.prompt()
	// 			.user(userText)
	// 			.call()
	// 			.chatResponse();

	// 	String responseContent = response.getResult().getOutput().getText();

	// 	var relevancyEvaluator = new RelevancyEvaluator(builder);

	// 	EvaluationRequest evaluationRequest = new EvaluationRequest(userText,
	// 			responseContent);

	// 	EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);

	// 	assertTrue(evaluationResponse.isPass(), "Response is not relevant to the question");

	// }

}
