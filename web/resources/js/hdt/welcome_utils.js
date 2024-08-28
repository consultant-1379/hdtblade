/*
 * welcome_utils.js - JS code for welcome page.
 * Author: escralp
 * Date: 18/03/2010
 *
 */

// Using a global variable to cache parameter rsults from AJAX call.
var parameters_results_cache;
var system_selected;
var network_selected;

$(document).ready(function() {

    install_ajax_spinner();
    
    $("input:radio").change(function() {
        system_selected_event();
    });

    $("#submit-button").button({
        icons: {
            primary: "ui-icon-check"
        }
    }).click(function() {
        // Show a notice when submitting the form.
        enable_activity_indicator();
        post_parameter_changes($("#param-section"));
    });
    
    // Set the active link for this page.
    activate_link("main-nav-1");
    
});

function before_parameters_update() {    
    // Hide the parameter explanation table.
    $("#param-section").hide();
    $("#network-selection").removeClass("last");
    $("#dimensioning-par-section").show();
}

function before_networks_update() {   
    $("#radio-prod").removeClass("last");
    $("#network-par-section").show();
}

// Render the networks selection div.
function render_networks_div(result_data) {
    var button_area = $("#network-sel");

    if (result_data != null && !jQuery.isEmptyObject(result_data)) {
        var button_container = "<div id=\"network-sel\" class=\"button-container\">";
        var radio_btn_str = "";        
               
        radio_btn_str = "<ul class=\"radio-par-list\">";
        $.each(result_data, function(index, element) {
            radio_btn_str += "<li><input type=\"radio\" class=\"radio\" name=\"networkId\" id=\"radio_net_" + index + "\" value=\"" + element.name +
            "\" name=\"network\"/><label for=\"radio_net_" + index + "\">";
            radio_btn_str += element.description;
            radio_btn_str +="</label></li>";
        });      
        button_container += radio_btn_str + "</ul></div>";
                
        // This renders the buttons.
        button_area.replaceWith(button_container);
        
        var button_list = $("input[name='networkId']");                
        // If the lenght of the list is 1, select the only button.
        if (result_data.length == 1) {
            $(button_list[0]).attr("checked", "checked");            
            network_selected_event();
        } else {
            button_list.change(function() {
                network_selected_event();
            });
        }
        
    } else {
        insert_ajax_error_msg("#network-selection", "main-input-load-error", "Error loading Networks. No applicable Networks found.", true);
    }
}

// Render parameter selection div.
function render_parameters_div(result_data) {
    var button_area = $("#dimensioning-par-radiobtn");

    // FIXME: Should consider using a template here too.
    if (result_data != null) {
        var button_container = "<div id=\"dimensioning-par-radiobtn\" class=\"button-container\">";
        var radio_btn_str = "";
                
        var main_dim_par_map = result_data.mainDimParMap;        
        var empty_par_map = jQuery.isEmptyObject(main_dim_par_map);
        var success = 0;

        // Check status returned data.
        if (empty_par_map != true) {
            radio_btn_str = "<ul class=\"radio-par-list\">"
            $.each(main_dim_par_map, function(bundle_name, param_list) {
                radio_btn_str += "<li><input type=\"radio\" class=\"radio\" name=\"bundleId\" id=\"" + bundle_name + "\" value=\"" + bundle_name +
                "\" name=\"size\"/><label for=\"" + bundle_name +"\">";
                radio_btn_str += bundle_name;
                radio_btn_str +="</label></li>";
            });
            success = 1;
            button_container += radio_btn_str + "</ul></div>";
            // This renders the buttons.
            button_area.replaceWith(button_container);
        } else {
            insert_ajax_error_msg("#parameter-sel", "main-input-load-error", "Error loading Main Dimensioning Parameters.", true);
        }

        // Install radio button change handlers.
        if (success == 1) {
            // Cache the retrieved result.
            parameters_results_cache = result_data;

            // Get all the buttons.
            var button_list = $("input[name='bundleId']");
            // Set the first button in the list to checked.
            $(button_list[0]).attr("checked", "checked");
            // This draws the initial table.
            update_parameter_section_div();
            
            var radio_buttons = $("input:[name='bundleId']");
            radio_buttons.change(function() {
                update_parameter_section_div();
            });
        } else {
            parameters_results_cache = null;
        }
    }
}

// Event handler for changing the system.
function system_selected_event() {
    system_selected = $("input[name='systemId']:checked").val();

    // If we had a previous selection, hide the old results.
    $("#dimensioning-par-section").hide();
    $("#network-selection").addClass("last");

    if (system_selected != undefined) {
        // Need to update networks selection.
        update_networks_ajax(system_selected);
    }
}

// Event handler for changing the network. This implies the system has already been specified
function network_selected_event() {
    network_selected = $("input[name='networkId']:checked").val();
    
    if (network_selected != undefined ) {
        // Need to update parameter selection.
        update_parameters_ajax();
    }
}

// Retrive the map of main input parameter lists.
function update_networks_ajax(system_selected) {
    $.ajax({
        type: 'GET',
        async: false,
        url: 'welcome/get_networks.htm',
        beforeSend: function() {
            before_networks_update();
        },
        complete: function() {            
        },
        data: {
            "systemId" : system_selected
        },
        timeout: 5000,
        success: function(result_data, statusText, jqXHR) {            
            if (data_returned_is_json(jqXHR)) {
                render_networks_div(result_data);
            } else {
                insert_ajax_error_msg("#network-selection", "main-input-load-error", "Error loading Networks.", true);
            }            
        },
        error: function (XMLHttpRequest, text_status, error_thrown) {
            insert_ajax_error_msg("#network-selection", "main-input-load-error", "Error loading Networks.", true);
        }
    });
}

// Retrive the map of main input parameter lists.
function update_parameters_ajax() {    
    $.ajax({
        type: 'GET',
        async: false,
        url: 'welcome/get_main_parameters.htm',       
        beforeSend: function() {
            before_parameters_update();
        },
        complete: function () {            
        },
        data: {
            "systemId" : system_selected,
            "networkId" : network_selected
        },
        timeout: 5000,
        success: function(result_data, textStatus, jqXHR) {
            if (data_returned_is_json(jqXHR)) {
                render_parameters_div(result_data);
            } else {
                insert_ajax_error_msg("#parameter-sel", "main-input-load-error", "Error loading Main Dimensioning Parameters.", true);
            }
        },
        error: function (XMLHttpRequest, text_status, error_thrown) {            
            insert_ajax_error_msg("#parameter-sel", "main-input-load-error", "Error loading Main Dimensioning Parameters.", true);            
        }
    });
}    


function update_parameter_section_div() {
    // This renders the parameter form.
    if (parameters_results_cache != null) {
        var result_div = new EJS({
            url: 'resources/templates/main_parameter_form.ejs'
        }).render(parameters_results_cache);

        $("#param-section").replaceWith(result_div);
    }
}

function post_parameter_changes(my_form) {
    var post_data = gather_post_data(my_form);
    
    // Can be empty if there were no changes.
    if ($.isEmptyObject(post_data)) {
        post_data = new Object();
    }
               
    // We need this to update the engine.
    post_data["bundleId"] = $("input[name=bundleId]:checked").val();
        
        // Convert to a JSON object.
    var post_data_json = JSON.stringify(post_data);        
        
    $.ajax({
        type: 'POST',
        url: "welcome/set_main_parameters.htm",            
        data: post_data_json,
        contentType: "application/json; charset=utf-8",
        timeout: 5000,            
        async: false,
        success: function(result_data) {
        // Pass the POST on.
        },
        error: function (xhr, status, errorThrown) {
            // FIXME: Need more sophisticated error handling!                
            alert("Error posting updated parameters. Status code: " + status);
        }
    });    
}
        