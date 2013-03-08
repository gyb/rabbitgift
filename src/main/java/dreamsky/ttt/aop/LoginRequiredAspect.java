package dreamsky.ttt.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import dreamsky.ttt.user.User;

@Aspect
@Component
public class LoginRequiredAspect {

	@Around("@annotation(LoginRequired) && args(.., model, session)")
	public Object checkLogin(ProceedingJoinPoint pjp, Model model, HttpSession session) throws Throwable {
		User user = (User)session.getAttribute("user");
		if (user == null) {
			model.addAttribute(new User());
			return "redirect:/user/login";
		}
		
		return pjp.proceed();
	}
}
