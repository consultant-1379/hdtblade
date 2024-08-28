/*
 * dimensioning_utils.js - JS code for dimensioning page.
 * Author: escralp
 * Date: 17/03/2010
 *
 */

var alt_cont_handler_installed = new Object();

//
//  The "document ready" callback. This initialises all the main jQuery stuff.
//
$(document).ready(function() {    
    install_ajax_spinner();

    // Submit button style.
    $("#submit-button").button({
        icons: {
            primary: "ui-icon-check"
        }
    });

    // Go-to-top button style and functionality.
    $("#go-top-button").button({
        icons: {
            primary: "ui-icon-carat-1-n"
        }
    }).click(function() {
        window.scrollTo(0, 0);
    });
   
    // Set the active link for this page.
    activate_link("main-nav-2");

    // This makes the link act like a submit button.
    submit_link("main-nav-3", null, "dimensioningForm");

    setup_session_reset_dialog("main-nav-1");
    
    // Fetch the results data and render the page.
    get_page_ajax();     
});

function get_page_ajax() {
    $.ajax({
        type: 'GET',
        url: 'dimensioning/get_rendering_data.htm',
        timeout: 5000,
        beforeSend: function() {                      
        },
        complete: function() {                       
        },
        success: function(result_data) {           
            render_results(result_data);                        
            regenerate_explain_dialog();
            setup_parameter_boxes();                        
            setup_alternative_boxes();            
            install_submit_hook("dimensioningForm");
            install_input_field_hook("dimensioningForm");

            // This allows us to submit APP quantity changes.
            install_submit_app_changes_hook("dimensioningForm");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            ajax_error_results();        
        }
    }); 
}

// This function is invoked if a user hits OK in an update dialog.
function post_parameter_changes(my_form, role_name) {
    var post_data = gather_post_data(my_form);

    // Post data out to the server as JSON object.
    if (!$.isEmptyObject(post_data)) {
        // Add role and system name to object. This is needed in the engine.
        post_data["roleName"] = role_name;        

        // Convert to a JSON object.
        var post_data_json = JSON.stringify(post_data);
        
        $.ajax({
            type: 'POST',
            url: "dimensioning/change_parameters.htm",
            data: post_data_json,
            contentType: "application/json; charset=utf-8",
            timeout: 5000,
            beforeSend: function() {                             
            },
            complete: function() {               
            },
            success: function(result_data) {
                // Kick off page regeneration. This renders the (potentially new) results.
                clear_page();
                get_page_ajax();
            },
            error: function (xhr, status, errorThrown) {
                // FIXME: Need more sophisticated error handling!
                // Could evaluate the return code from the server component.
                alert("Error posting updated parameters.");
            }
        });
    }
}

// This renders the results table.
function render_results(rendering_data) {    
    var no_par_change = Boolean(rendering_data["noParChanges"]);
    var no_app_change = Boolean(rendering_data["noAPPChanges"]);
    
    // Check if either parameters or APP numbers or both have changed.
    if (!(no_par_change && no_app_change)) {
        $("#bundle-heading").replaceWith("<td class='tbl-heading custom-config'>Custom Configuration</td>");
        $("#bundle-desc").replaceWith("<td></td>");
    }

    var result_rows = new EJS({
        url: 'resources/templates/row_template_dimensioning.ejs'
    }).render(rendering_data);
    $(".result-rows").replaceWith(result_rows);
        
    // Append the last row from the template.
    var last_row_anchor = $(".last-row");    
    $.get('resources/templates/end_row.tmpl',
        function success(html, text_status, jqxhr) {
            $(last_row_anchor).after(html);
        });
}

// !!!
// FIXME: THIS IS VERY EXPENSIVE!
// !!!
function clear_page() {
    // Remove all result rows and the last row.
    $(".result-rows").remove();
    $(".end-row").remove();
    
    // Remove jQuery UI dialog residuals.    
    $(".ui-dialog").empty().remove();

    // Remove parameter dialog divs.
    $(".parameter-dialog").empty().remove();
    $(".explain-dialog").empty().remove();

    // Add new anchor after head for templating to begin again.
    $("thead").after("<tbody class=\"result-rows\"/>");

    // This needs to be set up again.
    setup_session_reset_dialog("main-nav-1");
    
    // Need to set this up again as DOM has changed.
    alt_cont_handler_installed = undefined; // This may be superflous??
    alt_cont_handler_installed = new Object();
}

// Creates a jQuery UI dialog.
function setup_parameter_boxes() {
    // Dialogs are only set up when needed. Speeds up initial rendering.
    $(".modify-parameter-link").click(function() {       
        var role_name = $(this).attr('id');       
        
        // The name of the dialog div.
        var name = "#parameter-dialog-" + role_name;
        // The name of the content div.
        var content_name = name + "-content";       
        var this_dialog_div = $(name);
        // Define the dialog options.
        var dialog_options = {
            autoOpen: true,
            modal: true,
            minWidth: 580,
            minHeight: 200,
            position: 'center',
            title: 'Change Parameters',
            resizable: false,
            draggable: false,
            overlay: {
                opacity: 0.8,
                background: 'black'
            },
            buttons: {
                "Ok" : function() {
                    // Post updates to the server.
                    var anchor = $("#" + role_name + "-form");
                    post_parameter_changes(anchor, role_name);
                    $(this).dialog("close");
                },
                "Cancel": function() {
                    $(this).dialog("close");
                }
            }
        }
        
        // Retrieve the content and add to the dialog.
        get_parameter_box_content_ajax(role_name, content_name);
        this_dialog_div.dialog(dialog_options);
    });
}

//
// Alternatives Selection Box
//
function setup_alternative_boxes() {
    // Dialogs are only set up when needed. Speeds up initial rendering.
    $(".modify-alt-link").click(function() {       
        var role_name = $(this).attr('id');        
        
        // The name of the dialog div.
        var name = "#alternatives-dialog-" + role_name;
        // The name of the content div.
        var content_name = name + "-content";       
        var this_dialog_div = $(name);
        // Define the dialog options.
        var dialog_options = {
            autoOpen: true,
            modal: true,
            minWidth: 320,
            minHeight: 200,
            position: 'center',
            title: 'Select Hardware Alternative',
            resizable: false,
            draggable: false,
            overlay: {
                opacity: 0.8,
                background: 'black'
            },
            buttons: {
                "Ok" : function() {                                        
                    var root = $("div.alt-content:.selected");
                    if (!$.isEmptyObject(root)) {
                        var selected_id = root.attr('id');                        
                        post_alternative_changes(role_name, selected_id);
                    }
                    
                    $(this).dialog("close");
                },
                "Cancel": function() {
                    $(this).dialog("close");
                }
            }
        }
        
        // Retrieve the content and add to the dialog.
        get_alternatives_box_content_ajax(role_name, content_name);        
        this_dialog_div.dialog(dialog_options);
    });
}

function setup_alt_content_handlers(role_name) {
       
    //
    // Hover handler.
    //
    var root = $("#alt-" + role_name);
    
    if (root == undefined) {
        return;
    }
    var content_root = root.find(".alt-content");
    
    content_root.hover(
        function(event) {
            // Hover in handler
            activate_alternative($(this));            
        }, 
        function(event) {
            // Hover out handler
            deactivate_alternative($(this));            
        });
    	
	
    //
    // Click handler.
    //   
    content_root.click(function() {
        // Uncheck the previously selected item.
        var root = $(this);
        // Clicking only works on items that are neither deprecated or currently selected.
        if (!(root.hasClass("deprecated") || root.hasClass("selected"))) {			
            $(".alt-content").each(function() {
                // Find in all alt-content classes the one that IS selected.
                if ($(this).hasClass("selected")) {
                    // Deactivate and remove the selected label.
                    deactivate_alternative($(this), true);
                    $(this).removeClass("selected");				
                }
            });
		
            // The clicked one is now selected.				
            $(this).addClass("selected");
        }		
    });                    
}

function activate_alternative(root) {
    if (!(root.hasClass('selected'))) {	    		
        if (!root.hasClass('deprecated')) {
            root.find(".alt-h1").append("<span class='ui-icon ui-icon-check check-status'></span>");
        } else {
            root.find(".alt-h1").append("<span class='ui-icon ui-icon-cancel check-status'></span>");
        }
    }
}

function deactivate_alternative(root, force) {	
    // Hover out handler
    if (!(root.hasClass('selected')) || force) {
        root.find("span:last").remove();
    }
}

function post_alternative_changes(role_name, selected_id) {
    $.ajax({
        type: 'GET',
        url: 'dimensioning/submit_changed_alternative.htm',
        data: {
            'roleName' : role_name,
            'selectedId' : selected_id            
        },            
        timeout: 5000,
        async: false,
        success: function() {               
            // Kick off page regeneration. This renders the (potentially new) results.
            clear_page();
            get_page_ajax();                
        },
        error: function (xhr, status, errorThrown) {
            if (status == "error") {
                alert("There was an error contacting the server, please reload the page and try again.");
            }
        }
    });  
}

//
// FIXME: This should generate a error list per role. Also there should be one for generic errors.
//
function regenerate_explain_dialog() {
    // Configure the dialog UI widget.
    $(".explain-dialog").dialog( {
        autoOpen: false,
        modal: true,        
        title: 'HDT Engine Error Log',
        height: 350,
        minWidth: 650,
        /*width: 'auto',
        height: 'auto',*/
        position: 'center',
        resizable: false,
        draggable: false,
        overlay: {
            opacity: 0.8,
            background: 'black'
        },
        buttons: {
            "Close" : function() {
                $(this).dialog("close");
            }
        }
    });

    $(".explain-link").click(function() {
        // Navigate to the dialog in the DOM structure that needs to be opened.
        var this_dialog = $("#explain-dialog-"+$(this).attr('id'));        
        // Open the dialog that was previously configured.
        this_dialog.dialog("open");
    });
}

function ajax_error_results() {
    var error_area = "<tr><td><div class=\"ajax-error-box-res\">";
    error_area += "There was an error retrieving the information from the server. Please reload this page and try again.</div></td><td></td></tr>";
    $(".result-rows").replaceWith(error_area);
}

function get_parameter_box_content_ajax(role_name, div_name) {
    // Diable the global spinner as we have a local one.
    disable_ajax_spinner();
    
    $.ajax({
        type: 'GET',
        url: 'dimensioning/change_parameters.htm',
        data: {
            'roleName' : role_name            
        },
        timeout: 5000,
        beforeSend: function() {
            // Local AJAX spinner.
            $(div_name).html("<div><img id=\"ajax-load-img\" src=\"resources/images/ajax-loader.gif\" alt=\"Loading...\"/></div>");
        },        
        success: function(result_data) {
            $(div_name).replaceWith(result_data);
            install_reset_link_handler(role_name);
        },
        error: function (xhr, status, errorThrown) {
            if (status == "error") {                
                var msg = "Error loading detailed parameter content for role " + role_name + ". Please reload the page and try again." +
                "<br/><br/><b>Status Code</b>: " + xhr.status +
                "<br/><b>Status Message</b>: " + xhr.statusText;
                insert_ajax_error_msg(div_name, "parameter-ajax-error", msg, false);
            }
        }
    });
    
    enable_ajax_spinner();
}

function get_alternatives_box_content_ajax(role_name, div_name) {
    // Diable the global spinner as we have a local one.
    disable_ajax_spinner();
    
    $.ajax({
        type: 'GET',
        url: 'dimensioning/get_hw_alternatives.htm',
        data: {
            'roleName' : role_name            
        },
        timeout: 5000,
        beforeSend: function() {
            // Local AJAX spinner.
            $(div_name).html("<div><img id=\"ajax-load-img\" src=\"resources/images/ajax-loader.gif\" alt=\"Loading...\"/></div>");
        },        
        success: function(result_data) {            
            $(div_name).replaceWith(result_data);
            
            // Only set up the hover handlers once.
            if (alt_cont_handler_installed[div_name] != 'true') {                
                setup_alt_content_handlers(role_name);
                alt_cont_handler_installed[div_name] = 'true'; 
            }
        },
        error: function (xhr, status, errorThrown) {
            if (status == "error") {                
                var msg = "Error loading alternatives content for role " + role_name + ". Please reload the page and try again." +
                "<br/><br/><b>Status Code</b>: " + xhr.status +
                "<br/><b>Status Message</b>: " + xhr.statusText;
                insert_ajax_error_msg(div_name, "parameter-ajax-error", msg, false);
            }
        }
    });
    
    enable_ajax_spinner();
}

// Implements the reset to default functionality.
function install_reset_link_handler(role_name) {
    // Find all links in the current form.
    var root_id_str = "#" + role_name + "-form";
    var form_links = $(root_id_str + " a");

    form_links.click(function click_fn() {
        // Refers to the current link.
        var me = $(this);
        var id = me.attr("id");
        var payload = get_payload($(this));
        var parameter_default = payload["default"];
        var parameter_type = payload["type"];

        // Now locate the input field, and set the value, according to it's type.
        var field_anchor = $(root_id_str).find("#" + id);

        if (parameter_type == "text") {
            field_anchor.val(parameter_default);
        } else if (parameter_type == "checkbox") {
            if (parameter_default == "true") {
                field_anchor.attr("checked", "checked");
            } else if (parameter_default == "false") {
                field_anchor.attr("checked", null);
            } else {
        // Do nothing.
        // FIXME: This is an error.
        }
        } else {
    // Do nothing.
    // FIXME: This would constitute an error.
    }
    });
}

//
// As opposed to a complete form submit, this only submits the APP changes.
//
function install_submit_app_changes_hook(form_name) {
    var form_anchor = $("#" + form_name);

    $(".submit-app-changes").click(function(){
        post_quantity_changes(form_anchor, true);
    });
}

//
// Install handler to send out quantity update before finally submitting the form.
//
function install_submit_hook(form_name) {
    var form_anchor = $("#" + form_name);

    form_anchor.submit(function() {
        post_quantity_changes(form_anchor, false);
    });
}

function post_quantity_changes(form_anchor, refresh) {
    var input_list = form_anchor.find("input");
    var post_data = new Object();   

    // Gather info from each input field.
    input_list.each(function get_field_values() {
        var me = $(this);
        // id points to the APP number.
        var id = me.attr("id");
        var payload = get_payload($(this));
        var role_name = payload["role_name"];
        var previous_val = payload["previous"];

        // Check if something has changed.
        var curr_val = me.val();
        if (curr_val != previous_val) {
            // Entry has changed.
            var app = new Object();

            app["number"] = id;
            app["quantity"] = curr_val;

            // Create a new list, if none exists
            if (post_data[role_name] == undefined) {
                post_data[role_name] = new Array();
            }

            // Append element to the APP list for the current role.
            post_data[role_name].push(app);
        }
    });

    if (!$.isEmptyObject(post_data)) {
        // Convert to a JSON object.
        var post_data_json = JSON.stringify(post_data);
        
        $.ajax({
            type: 'POST',
            url: "dimensioning/change_quantities.htm",
            data: post_data_json,
            contentType: "application/json; charset=utf-8",
            timeout: 5000,
            async: false,
            success: function() {
                if (refresh) {
                    // Kick off page regeneration. This renders the (potentially new) results.
                    clear_page();
                    get_page_ajax();
                }
            },
            error: function (xhr, status, errorThrown) {
                if (status == "error") {
                    alert("There was an error contacting the server, please reload the page and try again.");
                }
            }
        });                
    }
}

//
// This gets invoked when the user clicks on an APP qty input field.
//
function install_input_field_hook(form_name) {
    var form_anchor = $("#" + form_name);
    var input_list = form_anchor.find("input");

    // Install 'click' handlers.
    input_list.click(function install_handlers() {
        var anchor = $(this);

        // Highlight the field.
        anchor.addClass("changingPar");
        anchor.addClass("user-qty");

        // Clear field.
        anchor.val("")

        // Extract the role name that this APP belongs to.
        var tmp = get_payload(anchor);
        var role_name_id_pre = "#" + tmp["role_name"];

        // Activate the "Submit Changes" link".
        $(role_name_id_pre + "-app-modified-submit").show();

        // Activate the notes section.
        $(role_name_id_pre + "-custom-config-notes").show();

        // Activate the "Changed APPs" note.
        $(role_name_id_pre + "-app-modified-note").show();
    });
}




