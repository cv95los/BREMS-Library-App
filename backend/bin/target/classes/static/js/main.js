$(document).ready(function(){
  /*scroll to top
	$(function () {
		$.scrollUp({
	        scrollName: 'scrollUp', // Element ID
	        scrollDistance: 300, // Distance from top/bottom before showing element (px)
	        scrollFrom: 'top', // 'top' or 'bottom'
	        scrollSpeed: 300, // Speed back to top (ms)
	        easingType: 'linear', // Scroll to top easing (see http://easings.net/)
	        animation: 'fade', // Fade, slide, none
	        animationSpeed: 200, // Animation in speed (ms)
	        scrollTrigger: false, // Set a custom triggering element. Can be an HTML string or jQuery object
					//scrollTarget: false, // Set a custom target element for scrolling to the top
	        scrollText: '<i class="fa fa-angle-up"></i>', // Text for element, can contain HTML
	        scrollTitle: false, // Set a custom <a> title if required.
	        scrollImg: false, // Set true to use image
	        activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
	        zIndex: 2147483647 // Z-Index for the overlay
		});
	});
*/
  /*
  * Ajax Paginate
  */
  var contBooks = 0;
  var contMagazines = 0;
  var coontAll = 0;
  var contSearch = 0;
  $("#moreBooks").on("click", function() {
    contBooks++;
    $.get("/moreBooks", {
        page: contBooks
      })
      .done(function(data) {
        if (!$.trim(data)) {
          $("#moreBooks").attr("disabled", "disabled");
        } else {
          $("#books .listItems").append(data);
        }
      });
  });
  $("#moreMagazines").on("click", function() {
    contMagazines++;
    $.get("/moreMagazines", {
        page: contMagazines
      })
      .done(function(data) {
        if (!$.trim(data)) {
          $("#moreMagazines").attr("disabled", "disabled");
        } else {
          $("#magazines .listItems").append(data);
        }
      });
  });
  $("#moreAllShelf").on("click", function() {
    coontAll++;
    $.get("/moreAllShelf", {
        page: coontAll
      })
      .done(function(data) {
        if (!$.trim(data)) {
          $("#moreAllShelf").attr("disabled", "disabled");
        } else {
          $("#allShelf .listItems").append(data);
        }
      });
  });
  $("#moreSearch").on("click", function() {
	  contSearch++;
	    $.get("/moreSearch", {
	        page: contSearch,
	        mySearch: $('#searchName span').text()
	      })
	      .done(function(data) {
	        if (!$.trim(data)) {
	          $("#moreSearch").attr("disabled", "disabled");
	        } else {
	          $("#results .listItems").append(data);
	        }
	      });
  });
});
