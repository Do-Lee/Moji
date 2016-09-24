var config = {
    // Language for the mirror (currently not implemented)
    language : "ko",
    greeting : ["[나만의學Up비법]학습방법 공모 안내","[학습법특강] 2016학년도 1학기 CTL 학습역량강화특강 시리즈 안내"," ","[교수법] 2016학년도 제 1차 교수법 워크숍 실시 ","[배움품앗이]3,4주차 출석현황","[학과튜터링] 학과 지도교수 배정 안내","[배움품앗이] 2차시 출석 현황입니다. ","[명지 FLC] 2016-1학기 신입생 합격자 및 OT 안내"," "," [명지 C.C] 최종 선발 팀 및 OT 안내","[명지 배움품앗이] 2016-1학기 팀 선발 결과 및 OT안내","[학과튜터링] 2016-1 학과튜터링 선발 결과","[명지 튜터룸] 2016-1학기 명지 튜터룸 2차 선발 결과 및 OT 안내"," ","[학과튜터링] 튜티 추가 모집 안내","릴레이 직무 특강 안내","[인문] 2016학년도 하계 국내현장학습(인턴십) 참가자 모집"," ","[자연]오스템임플란트(주) 2016 상반기 경영IT연구 개발 생산 영업 추천채용"," [인문] 2016 채용 및 기업설명회 참가자 모집 안내(계룡건설)"," ","[자연]2016 하반기 공채 대비 공모전 준비반 모집 안내"," 2016년 장기 WEST(18개월) 예비 지원자 설명회 안내"," [자연]한화생명 기업 설명회 개최"," [자연]2016년 글로벌취업상담회 개최 안내"," "," [자연]제1회 이데일리 취업설명회개최 안내","취업스터디 9기 모집"," [인문] 해외취업 전략 도전 비법 특강 1탄"," "," [자연]CREW FACTORY 승무원 취업특강 개최","2016-하계 및 2학기 해외현장학습(인턴십) 선발 공고( 파견국가 : 호주 독일)"," ","[인문] 2016 선배와의 취업멘토링 참가자 모집 안내","[인문] 한국TOEIC위원회와 함께하는 2016 新TOEIC 릴레이 특강", " " ], // An array of greetings to randomly choose from
    // forcast.io
    forcast : {
        key : "8d1303739586a0b863e9d41fcfa19cde", // Your forcast.io api key
        units : "auto" // See forcast.io documentation if you are getting the wrong units
    },
    // Calendar (An array of iCals)
    calendar: {
      icals : ["....."],
      maxResults: 9, // Number of calender events to display (Defaults is 9)
      maxDays: 365 // Number of days to display (Default is one year)
    },
    traffic: {
      key : "KtuEHjQEvCZOqMsLkpl0~cdf6uLYTzbwV0wy_YrDvuQ~Aow3U_Y6t_GSsC1xL9m6GD32e8q6Xe9mWJk6uyiB8nyD3pxewnRes2qB9pLOUiVQ", // Bing Maps API Key
      mode : "Transit", // Possibilities: Driving / Transit / Walking
      origin : "Youngin", // Start of your trip. Human readable address.
      destination : "Seoul", // Destination of your trip. Human readable address.
      name : "명지대학교 인문캠퍼스", // Name of your destination ex: "work"
      reload_interval : 5 // Number of minutes the information is refreshed
    },

    youtube: {
      key:"....."
    },

    subway: {
      key:"....."
    },
    soundcloud: {
    	key:"....."
    }
}
