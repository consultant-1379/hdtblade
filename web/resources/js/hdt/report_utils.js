/*
 * report_utils.js - JS code for report page.
 * Author: escralp
 * Date: 10/03/2010
 *
 */

//
//  The "document ready" callback. This initialises all the main jQuery stuff.
//
$(document).ready(function() {
    // This pops open the print window.
    /*
     * DISABLED PRINT PREVIEW. RS12082011
     $(".print-button").button({
        icons: {
            primary: "ui-icon-print"
        }
    }).click(function() {
        popUp("report.htm?view=print_preview", "PrintPreview");
    });
     */

    // Go-to-top button style and functionality.
    $("#go-top-button").button({
        icons: {
            primary: "ui-icon-carat-1-n"
        }
    }).click(function() {
        window.scrollTo(0, 0);
    });
    
    $(".email-button").button({
        icons: {
            primary: "ui-icon-disk"
        }
    }).click(function() {
        popUp("report.htm?view=excel", "Excel");
    });

    // Set the active link for this page.
    activate_link("main-nav-3");

    setup_session_reset_dialog("main-nav-1");
    
    // Fetch the results data and render the page.
    get_page_ajax();
});

function get_page_ajax() {
    $.ajax({
        type: 'GET',
        url: 'report/get_rendering_data.htm',
        timeout: 5000,
        beforeSend: function() {                      
        },
        complete: function() {                       
        },
        success: function(result_data) {           
            render_results(result_data);            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            ajax_error_results();        
        }
    }); 
}

// This renders the results table.
function render_results(rendering_data) {    
    //var no_par_change = Boolean(rendering_data["noParChanges"]);
    //var no_app_change = Boolean(rendering_data["noAPPChanges"]);
    
    // Check if either parameters or APP numbers or both have changed.
    //if (!(no_par_change && no_app_change)) {
    //    $("#bundle-heading").replaceWith("<td class='tbl-heading custom-config'>Custom Configuration</td>");
    //    $("#bundle-desc").replaceWith("<td></td>");
    //}

    var result_rows = new EJS({
        url: 'resources/templates/row_template_report.ejs'
    }).render(rendering_data);
    $("#condensed-report").replaceWith(result_rows);          
}

function ajax_error_results() {
    var error_area = "<div id='condensed-report' class=\"ajax-error-box-res\">";
    error_area += "There was an error retrieving the information from the server. Please reload this page and try again.</div>";
    $("#condensed-report").replaceWith(error_area);
}