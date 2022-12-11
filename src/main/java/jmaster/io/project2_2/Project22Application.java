package jmaster.io.project2_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * implements WebMvcConfigurer : để chỉ định đọc đa ngôn ngữ
 *
 * @EnableJpaAuditing : spring hỗ trợ tự gen, tự set và trước khi thêm sửa xoá có thể can thiệp( chặn ngang lấy 1 bản backup)
 */
@SpringBootApplication
@EnableJpaAuditing
public class Project22Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Project22Application.class, args);
    }

    /**
     * LocaleResolver : lưu lại ngôn ngữ mà người dùng chọn
     * SessionLocaleResolver : lưu ngôn ngữ đã chọn vào session
     * setDefaultLocale : set mặc định là 'en'
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("en"));
        return slr;
    }

    /**
     * LocaleChangeInterceptor :giống bộ lọc trong servlet
     * setParamName : set param trên đường dẫn là 'lang'
     * vd: hello?lang=vi thì sẽ chuyển sang đọc file _vi
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * addInterceptors() : đăng ký localeChangeInterceptor vào InterceptorRegistry thì spring mới nhận.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
