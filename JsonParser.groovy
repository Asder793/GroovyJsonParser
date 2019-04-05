import groovy.json.*

// Парсим JSONы
def textD = vars.get("DATA");
def responseD = new JsonSlurper().parseText( textD );
def jsonD = new JsonBuilder(responseD);

def textI = vars.get("impact");
def responseI = new JsonSlurper().parseText( textI );
def jsonI = new JsonBuilder(responseI);

def textSa = vars.get("serviceAsset");
def responseSa = new JsonSlurper().parseText( textSa );
def jsonSa = new JsonBuilder(responseSa);

def textSc = vars.get("serviceContract");
def responseSc = new JsonSlurper().parseText( textSc );
def jsonSc = new JsonBuilder(responseSc);

def textU = vars.get("urgency");
def responseU = new JsonSlurper().parseText( textU );
def jsonU = new JsonBuilder(responseU);

def textOw = vars.get("owner");
def responseOw = new JsonSlurper().parseText( textOw );
def jsonOw = new JsonBuilder(responseOw);

def textG = vars.get("group");
def responseG = new JsonSlurper().parseText( textG );
def jsonG = new JsonBuilder(responseG);

// Генерим Priority
def p_oid = vars.get("priority_oid");
def p_title = vars.get("priority_title");
def newPriority = """{ "oid": \"""" + p_oid + """\", "title": \"""" + p_title + """\" }""";
def prioritySlurped = new JsonSlurper().parseText(newPriority);
def priority = new JsonBuilder(prioritySlurped);

// Редактируем начальный JSON
jsonD.content.classification.impact = jsonI.content;
jsonD.content.classification.serviceAsset = jsonSa.content;
jsonD.content.classification.serviceContract = jsonSc.content;
jsonD.content.classification.urgency = jsonU.content;
jsonD.content.classification.priority = priority.content;
jsonD.content.workDetail.solution.userSolution = "";

// Формируем результирующий JSON
def newJson = """{"classification": null, "entityInfo": null, "initiator": null, "modifyDate": null, "oid": null, "owner": null, "petrolStation": null, "sla": {}, "solution": null, "systemInfo": {} }""";
def slurped = new JsonSlurper().parseText( newJson );
def jsonNew = new JsonBuilder(slurped);

// Формируем блок newOwner
def newJsonOw = """{ "address": null, "email": null, "floor": null, "fullName": null, "isPetrol": null, "isRim": null, "isVip": null, "mobilePhone": null, "office": null, "oid": null,    "organization": null, "territory": null }""";
def slurpedOw = new JsonSlurper().parseText( newJsonOw );
def jsonNewOw = new JsonBuilder(slurpedOw);

jsonNewOw.content.address = jsonOw.content.address;
jsonNewOw.content.email = jsonOw.content.email;
jsonNewOw.content.floor = jsonOw.content.floor;
jsonNewOw.content.fullName = jsonOw.content.fullName;
jsonNewOw.content.isPetrol = jsonOw.content.isPetrol;
jsonNewOw.content.isRim = jsonOw.content.isRim;
jsonNewOw.content.isVip = jsonOw.content.isVip;
jsonNewOw.content.mobilePhone = jsonOw.content.mobilePhone;
jsonNewOw.content.office = jsonOw.content.office;
jsonNewOw.content.oid = jsonOw.content.oid;
jsonNewOw.content.organization = jsonOw.content.organization;
jsonNewOw.content.territory = jsonOw.content.territory;

// Формируем блок newInitiator
def newInitiator = """{ "emailIntegration": null, "group": {"oid": null, "title": null }, "groupManagersList": null, "isAllGroups": true,    "isCurrentUserAnyGroupMember": true, "isCurrentUserMember": false,    "specialist": null }""";
def slurpedI = new JsonSlurper().parseText( newInitiator );
def jsonNewI = new JsonBuilder(slurpedI);

jsonNewI.content.group.oid = jsonG.content.oid;
jsonNewI.content.group.title = jsonG.content.title;
jsonNewI.content.groupManagersList = jsonG.content.groupManagersList;
jsonNewI.content.isCurrentUserMember = jsonG.content.isCurrentUserMember;

// Формируем блок newInfo
def newInfo = """{ "affectedAsset": {}, "deadline": null, "deadlineString": null, "description": null, "state": null, "statusReason": {}, "subject": null }""";
def slurpedIn = new JsonSlurper().parseText( newInfo );
def jsonNewIn = new JsonBuilder(slurpedIn);

jsonNewIn.content.deadline = jsonD.content.info.deadline;
jsonNewIn.content.description = jsonD.content.info.description;
jsonNewIn.content.state = jsonD.content.info.state;
jsonNewIn.content.subject = jsonD.content.info.subject;

// Собираем результат
jsonNew.content.classification = jsonD.content.classification;
jsonNew.content.solution = jsonD.content.workDetail.solution;
jsonNew.content.modifyDate = jsonD.content.modifyDate;
jsonNew.content.oid = jsonD.content.oid;
jsonNew.content.owner = jsonNewOw.content;
jsonNew.content.initiator = jsonNewI.content;
jsonNew.content.entityInfo = jsonNewIn.content;

vars.put( "JSON", jsonNew.toString() )


