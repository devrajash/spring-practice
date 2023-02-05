package pringboot1st.springboot1.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pringboot1st.springboot1.user.User;

@Service
public class JwtTokenData {

  public User getTokenAuthData(){
      return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
