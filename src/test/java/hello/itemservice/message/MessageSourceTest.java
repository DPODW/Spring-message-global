package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest  {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() {
        String result = messageSource.getMessage("hello", null, null);
        //code = messages.properties 에서 지정한 이름 ex)hello. 현재 locale 이 null 이라서 기본 프로퍼티인 messages.properties 가 적용됌

        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        //매칭되는 코드가 없을 때
        assertThatThrownBy(()-> messageSource.getMessage("no_code",null,null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        //매칭되는 코드가 없을 때
        String message = messageSource.getMessage("no_code", null, "기본메시지", null);
        assertThat(message).isEqualTo("기본메시지");
    }

    @Test
    void argumentMessage() {
        /*args(아큐먼트) 속성에 값 넣어보기
        프로퍼티에서 {0} 이라고 적힌 곳과 치환된다.
        ++ 배열이 기본 타입이다.
        */
        String message = messageSource.getMessage("hello.name",new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(messageSource.getMessage("hello",null,null)).isEqualTo("안녕");

        //Locale 을 지정할수 있다. 그러나 지금은 KOREA 가 없기 때문에 그냥 디폴트 프로퍼티가 작동된다.
        assertThat(messageSource.getMessage("hello",null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        /*Locale 을 ENGLISH 로 지정하게 되면, 우리가 만든 en_프로퍼티로 이동된다. (아마 스프링이 줄임말도 이해하는것 아닐까?)
        Locale 을 통해 이동할 프로퍼티를 특정할수 있다.*/
        assertThat(messageSource.getMessage("hello",null,Locale.ENGLISH)).isEqualTo("hello");
    }
}
