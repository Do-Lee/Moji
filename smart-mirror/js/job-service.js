(function(annyang) {
    'use strict';
    function JobService() {
        jQuery.ajax({
        type :"get",
        datatype:"json",
        url:"http://52.78.21.188/job.php",
        data: { get_param: 'result' }, 
        success:function(data){
        
            var str;
            var temp = JSON.parse(data).result;
            for (var i = 0; i < 10; i++) {
                str = temp[i].title;
                $('#jobOutput').append(str + '<br>'); 
             }
            $('#jobOutput').append('<br>'); 
      }
        }
        })
    }
    angular.module('SmartMirror')
        .factory('JobService', JobService);

}(window.annyang));