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
                    이제부터 api 추가/수정되는 부분은 아래에 써놓을게요. 궁금한점 이상한점 원하는점은 카톡으로 쏴주세요. \n
                    
                    [07/01 업데이트]
                    1. /users/{userId} 조회시 userType도 조회되도록 추가하였습니다. \n
                    2. /votes/hot과 /votes/suggestions에 iconUrl 정보를 포함하고, 각각의 투표 토픽에 유저의 투표 여부(isVoted) 컬럼도 추가했습니다.
                    
                    [06/30 업데이트]
                    1. 홈화면에 노출되는 오늘의 투표 조회 api를 추가하였습니다. (구체적인 오늘의 투표 선정 정책은 모르겠어서 일단 투표수 젤 낮은거로 땜빵)
                    
                    [06/23 업데이트]
                    1. 단일 투표 조회시 투표 마감 여부를 나타내는 isClosed 변수를 추가하였습니다. \n
                    2. 카테고리 조회시 백그라운드 컬러, 텍스트 컬러, 아이콘 url에 대한 정보들을 모두 업데이트 완료했습니다. \n
                    3. 여러개/한개 투표 조회시 참여자수를 나타내는 변수 이름을 participantCount로 통일/수정하였습니다. \n
                    4. 투표 선택 api가 변경되었습니다. 기존에 투표 옵션을 1개만 선택할 수 있던 부분들을 복수 선택 가능할 수 있도록 바꾸었습니다. 이에 따라 단일 투표 조회 api에서도 사용자가 선택한 chosenVoteOptionIds가 배열로 바뀌었습니다.
                    
                    [06/22 업데이트]
                    1. 투표 선택 api를 추가하였습니다.\n
                    2. 사용자의 투표 여부를 포함하는 단일 투표 조회 api를 추가하였습니다. \n
                    
                    [기타..]
                    1. 특정 투표에 대한 연관 콘텐츠 조회는 별도의 api로 빼려고 합니다. \n
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
