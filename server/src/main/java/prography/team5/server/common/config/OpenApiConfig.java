package prography.team5.server.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "사초생 API Docs",
        description = """
                    사초생 API 명세서입니다. \n
                    에러 문서 보러가기-> https://bit.ly/sachosaeng-error-docs \n
                    이제부터 api 추가/수정되는 부분은 아래에 써놓을게요. 궁금한점 이상한점 원하는점은 카톡으로 쏴주세요.
                    
                    [06/23 업데이트]
                    1. 단일 투표 조회시 투표 마감 여부를 나타내는 isClosed 변수를 추가하였습니다.
                    
                    [06/22 업데이트]
                    1. 투표 선택 api를 추가하였습니다.\n
                    2. 사용자의 투표 여부를 포함하는 단일 투표 조회 api를 추가하였습니다. \n
                    
                    [기타..]
                    1. 카테고리에서 조회되는 정보가 바뀔 수 있습니다. (backgroundColor에 대한 투명도?) \n
                    2. 특정 투표에 대한 연관 콘텐츠 조회는 별도의 api로 빼려고 합니다. \n
                    """
))
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "Access Token";
        SecurityScheme securityScheme = new SecurityScheme()
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(In.HEADER)
                .name("Authorization");

        String refreshSchemeName = "Refresh Token";
        SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
                .type(Type.APIKEY)
                .in(In.COOKIE)
                .name("Refresh");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(jwtSchemeName)
                .addList(refreshSchemeName);

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(
                        new Components()
                                .addSecuritySchemes(jwtSchemeName, securityScheme)
                                .addSecuritySchemes(refreshSchemeName, refreshTokenSecurityScheme)
                )
                .addSecurityItem(securityRequirement);
    }
}
