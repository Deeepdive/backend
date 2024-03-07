package deepdive.backend.slack;

import static java.util.Collections.singletonList;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackField;
import net.gpedro.integrations.slack.SlackMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile(value = {"local", "dev"})
public class SlackNotificationAspect {

	private final SlackApi slackApi;
	private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private final Environment env;

	public SlackNotificationAspect(@Value("${slack.webhook.error}") String webhook,
		ThreadPoolTaskExecutor threadPoolTaskExecutor, Environment env) {
		this.slackApi = new SlackApi(webhook);
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
		this.env = env;
	}

	@Around(value = "@annotation(deepdive.backend.slack.SlackNotification) && args(request, e)", argNames = "proceedingJoinPoint,request,e")
	public void slackNotification(
		ProceedingJoinPoint proceedingJoinPoint,
		HttpServletRequest request,
		Exception e) throws Throwable {

		proceedingJoinPoint.proceed();

		//HttpServletRequest를 RequestInfo라는 DTO에 복사
		RequestInfo requestInfo = new RequestInfo(request.getRequestURI(), request.getMethod(),
			request.getRemoteAddr());

		threadPoolTaskExecutor.execute(() -> sendSlackErrorMessage(requestInfo, e));
	}

	private void sendSlackErrorMessage(RequestInfo request, Exception e) {
		SlackAttachment slackAttachment = new SlackAttachment();
		slackAttachment.setFallback("Error");
		slackAttachment.setColor("danger");

		slackAttachment.setFields(
			List.of(
				new SlackField().setTitle("Exception class")
					.setValue(e.getClass().getCanonicalName()),
				new SlackField().setTitle("예외 메시지").setValue(e.getMessage()),
				new SlackField().setTitle("Request URI").setValue(request.requestURL()),
				new SlackField().setTitle("Request Method").setValue(request.method()),
				new SlackField().setTitle("요청 시간")
					.setValue(LocalDateTime.now()
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))),
				new SlackField().setTitle("Request IP").setValue(request.remoteAddress()),
				new SlackField().setTitle("Profile 정보")
					.setValue(Arrays.toString(env.getActiveProfiles()))
			)
		);

		SlackMessage slackMessage = new SlackMessage();
		slackMessage.setAttachments(singletonList(slackAttachment));
		slackMessage.setText("예상치 못한 에러가 발생했잔슴~");
		slackMessage.setUsername("deepdive");

		slackApi.call(slackMessage);
	}
}
