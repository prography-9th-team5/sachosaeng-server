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
                    
                    [08/25 업데이트]
                    1. 기존에는 data가 없을시 응답에 data 필드가 없었는데, 이제 모든 응답에 data 필드가 포함되도록 하였으며, data가 없을경우 data가 빈 문자열로 가도록 수정했습니다.
                    
                    [08/24 업데이트]
                    1. 회원가입(join)시 userType을 받도록 추가하였습니다.
                    
                    [08/23 업데이트]
                    1. 회원가입할때 이메일 중복시 상태코드 409를 반환하도록 수정하였습니다.\n
                    2. 단일 연관 콘텐츠 조회시(/api/v1/information/{informationId}) 소제목(subtitle)이 추가되었습니다. 없을경우 null이 표시됩니다.
                    
                    [08/06 업데이트]
                    1. 미리보기 형태의 항목들(인기투표목록, 카테고리별 투표목록, 오늘의 투표 등등 홈화면에 보이는 것들)에 투표 마감 여부를 나타내는 isClosed 컬럼이 전체적으로 추가되었어요.
                    
                    [08/04 업데이트]
                    1. 버전 관련 api들을 추가하였으니 확인해주세요.
                    
                    [07/25 업데이트]
                    1. 회원탈퇴 api를 추가하였습니다.
                    
                    [07/24 업데이트]
                    1. "전체" 아이콘 조회 요청시 GET /api/v1/categories/icon-data/all 보내면 iconUrl과 backgroundColor를 확인할 수 있습니다.
                    
                    [07/20 업데이트]
                    1. 유저 정보를 조회하는 api가 userId를 몰라도 조회할수 있도록 변경하였으니 꼭 확인해주세요. (변경전: /api/v1/users/{userId}) -> (변경후: /api/v1/users, authorization 헤더 토큰 필요} \n
                    2. 현재 인증 토큰이 있어야 사용할 수 있는 api에는 [인증 토큰 필요]라는 문구를 달아놨어요. 해당 문구가 없다면 인증 토큰이 없어도 조회할 수 있어요. \n
                    3. 단일 정보 콘텐츠 조회 api를 추가했어요.
                    
                    [07/13 업데이트]
                    1. voteId와 categoryId를 통해 연관 콘텐츠 3개를 조회하는 api를 만들었습니다. 정보 콘텐츠는 현재 더미데이터가 들어가 있는점 참고해주세여 \n
                    2. all 아이콘 -> https://sachosaeng.store/icon/all-2x.png
                    
                    [07/12 업데이트]
                    1. isVoted에 기존 하드코딩 되어 있던 부분을 유저의 투표 여부를 반영하도록 업데이트 하였습니다. 이제 true도 반환됩니다. \n
                    2. 투표 "목록을 조회"하는 api들은 인증 토큰 없이도 볼 수 있도록 수정하였습니다. 단, 홈화면의 관심 카테고리 투표목록 조회들은 안됩니다.
                    
                    [07/05 업데이트]
                    1. 카테고리별 인기투표 조회 api를 추가하였습니다. \n
                    
                    [07/04 업데이트]
                    1. 지금까지의 모든 api url 앞에 /api/v1을 추가하였습니다. \n
                    2. 카테고리별 투표 최신순 조회 api를 추가하였습니다. (isVoted는 false로 하드코딩 되어있음)\n
                    
                    [07/01 업데이트]
                    1. /users/{userId} 조회시 userType도 조회되도록 추가하였습니다. \n
                    2. /votes/hot과 /votes/suggestions에 iconUrl 정보를 포함하고, 각각의 투표 토픽에 유저의 투표 여부(isVoted) 컬럼도 추가했습니다.\n
                    3. /votes/suggestions -> /votes/suggestions/my로 변경되었습니다.\n
                    4. 소연님이 부탁하신 토큰없이 홈화면에서 전체 조회할때 쓰는 api는 /votes/suggestions/all로 만들었습니다. +인기투표조회(/votes/hot), 카테고리전체조회(/categories)도 토큰없이 가능해요. \n
                    5. 카테고리별 아이콘은 피그마에 나와있는 거 참고해서 18px(홈), 32px(카테고리조회, 투표 상세) 적용했는데 이상한점 있으면 이야기해주세요. \n
                    6. 오늘의 투표 api도 인기투표랑 같은 데이터들을 반환하도록 수정하였습니다.
                    
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
