(function(annyang) {
    'use strict';
    function EduService() {
        jQuery.ajax({
        type :"get",
        datatype:"json",
        url:"http://52.78.21.188/edu.php",
        data: { get_param: 'result' }, 
        success:function(data){
        
            var str;
            var temp = JSON.parse(data).result;
            for (var i = 0; i < 10; i++) {
                str = temp[i].title;
                str = str.split(":", 2);
                $('#eduOutput').append(str[1] + '<br>'); 
            }
            $('#eduOutput').append('<br>'); 
        }
        })
    }
    angular.module('SmartMirror')
        .factory('EduService', EduService);

}(window.annyang));