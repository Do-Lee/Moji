var config = {
    // Language for the mirror (currently not implemented)
    language : "ko",
greeting : ["Hi Moji", "Hi Myong Ji", "Hi Capstone"], 
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
      key:"AIzaSyA7nHYVXHAO94lmv6-fSmxYaG5QAEp42C0"
    },

    subway: {
      key:"....."
    },
    soundcloud: {
    	key:"....."
    }
}
