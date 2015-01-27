$(function() {
 
    Parse.$ = jQuery;
 
    // Replace this line with the one on your Quickstart Guide Page
   Parse.initialize("scrq331lx40lxt6ZOIKjxXRmA2dkav83XPYi6EEz", "3FNhMBUiaStRR75lVoKijR8TBrOKsCwjys33nPr2");
 
   var TestObject = Parse.Object.extend("TestObject");
var testObject = new TestObject();
testObject.save({foo: "bar"}).then(function(object) {
  alert("yay! it worked");
});
 
});