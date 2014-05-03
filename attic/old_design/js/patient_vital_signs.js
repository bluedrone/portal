function getPatientVitalSigns(id) {
  var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
  $.post("../app/getPatientVitalSigns", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    var columns = [
      {title:'Date', field:'date', type:'date'}, 
      {title:'Weight', field:'weight', type:'simple'},
      {title:'BMI', field:'bmi', type:'simple'},
      {title:'OFC', field:'ofc', type:'simple'},
      {title:'Temp', field:'temperature', type:'simple'},
      {title:'Pulse', field:'pulse', type:'simple'},
      {title:'Resp', field:'respiration', type:'simple'},
      {title:'Syst', field:'systolic', type:'simple'},
      {title:'Dia', field:'diastolic', type:'simple'},
      {title:'Ox', field:'oximetry', type:'simple'}
    ];
    patientVitalSigns = parsedData.patientVitalSigns;
    var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
    {items:patientVitalSigns, 
    title:'Vital Signs', 
    clickable:false, 
    columns:columns
    }); 
    $('#patient_health_issue_detail_table').html(s);
   
    var cellIndexMap = {};
    for (i=0;i<columns.length;i++) {
      cellIndexMap[i] = columns[i].field;
    }
    patientVitalSigns.reverse(); 
    app_chartMap = {};
    for (i=0;i<patientVitalSigns.length;i++) {
      var obj = patientVitalSigns[i];
      for (var property in obj) {
        if (obj.hasOwnProperty(property)) {
          if (property in app_chartMap == false){
            var values = [];
            app_chartMap[property] = values; 
          }
          if (property == 'date') {
            obj[property] = dateFormat(obj[property], 'mm/dd/yyyy');
          }
          app_chartMap[property].push(obj[property]);
        }
      }
    }
    var labels = app_chartMap['date'];
    var data = app_chartMap['weight'];
    renderLineChart(labels, data);
    
    $("th.highlightable:nth-child(2)").addClass('highlighted').siblings().removeClass('highlighted');
    $("td.highlightable:nth-child(2)").addClass('highlighted').siblings().removeClass('highlighted');
    
    $('th.highlightable').on('click', function(e){ 
      var cellIndex = this.cellIndex;	
      if (cellIndex > 0) {
        var jqCellIndex = cellIndex+1;
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
        $("td.highlightable:nth-child("+jqCellIndex+")").addClass('highlighted').siblings().removeClass('highlighted');
        var labels = app_chartMap['date'];
        var data = app_chartMap[cellIndexMap[cellIndex]];
        renderLineChart(labels, data);
      }
    });
  });
}
