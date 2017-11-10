/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
 	
package fr.paris.lutece.plugins.transparency.web;

import fr.paris.lutece.plugins.transparency.business.Appointment;
import fr.paris.lutece.plugins.transparency.business.AppointmentHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage Appointment features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageAppointments.jsp", controllerPath = "jsp/admin/plugins/transparency/", right = "TRANSPARENCY_APPOINTMENTS_MANAGEMENT" )
public class AppointmentJspBean extends AbstractManageAppointementsJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_APPOINTMENTS = "/admin/plugins/transparency/manage_appointments.html";
    private static final String TEMPLATE_CREATE_APPOINTMENT = "/admin/plugins/transparency/create_appointment.html";
    private static final String TEMPLATE_MODIFY_APPOINTMENT = "/admin/plugins/transparency/modify_appointment.html";

    // Parameters
    private static final String PARAMETER_ID_APPOINTMENT = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_APPOINTMENTS = "transparency.manage_appointments.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_APPOINTMENT = "transparency.modify_appointment.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_APPOINTMENT = "transparency.create_appointment.pageTitle";

    // Markers
    private static final String MARK_APPOINTMENT_LIST = "appointment_list";
    private static final String MARK_APPOINTMENT = "appointment";

    private static final String JSP_MANAGE_APPOINTMENTS = "jsp/admin/plugins/transparency/ManageAppointments.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_APPOINTMENT = "transparency.message.confirmRemoveAppointment";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "transparency.model.entity.appointment.attribute.";

    // Views
    private static final String VIEW_MANAGE_APPOINTMENTS = "manageAppointments";
    private static final String VIEW_CREATE_APPOINTMENT = "createAppointment";
    private static final String VIEW_MODIFY_APPOINTMENT = "modifyAppointment";

    // Actions
    private static final String ACTION_CREATE_APPOINTMENT = "createAppointment";
    private static final String ACTION_MODIFY_APPOINTMENT = "modifyAppointment";
    private static final String ACTION_REMOVE_APPOINTMENT = "removeAppointment";
    private static final String ACTION_CONFIRM_REMOVE_APPOINTMENT = "confirmRemoveAppointment";

    // Infos
    private static final String INFO_APPOINTMENT_CREATED = "transparency.info.appointment.created";
    private static final String INFO_APPOINTMENT_UPDATED = "transparency.info.appointment.updated";
    private static final String INFO_APPOINTMENT_REMOVED = "transparency.info.appointment.removed";
    
    // Session variable to store working values
    private Appointment _appointment;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_APPOINTMENTS, defaultView = true )
    public String getManageAppointments( HttpServletRequest request )
    {
        _appointment = null;
        List<Appointment> listAppointments = AppointmentHome.getAppointmentsList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_APPOINTMENT_LIST, listAppointments, JSP_MANAGE_APPOINTMENTS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_APPOINTMENTS, TEMPLATE_MANAGE_APPOINTMENTS, model );
    }

    /**
     * Returns the form to create a appointment
     *
     * @param request The Http request
     * @return the html code of the appointment form
     */
    @View( VIEW_CREATE_APPOINTMENT )
    public String getCreateAppointment( HttpServletRequest request )
    {
        _appointment = ( _appointment != null ) ? _appointment : new Appointment(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_APPOINTMENT, _appointment );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_APPOINTMENT, TEMPLATE_CREATE_APPOINTMENT, model );
    }

    /**
     * Process the data capture form of a new appointment
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_APPOINTMENT )
    public String doCreateAppointment( HttpServletRequest request )
    {
        populate( _appointment, request );

        // Check constraints
        if ( !validateBean( _appointment, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_APPOINTMENT );
        }

        AppointmentHome.create( _appointment );
        addInfo( INFO_APPOINTMENT_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_APPOINTMENTS );
    }

    /**
     * Manages the removal form of a appointment whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_APPOINTMENT )
    public String getConfirmRemoveAppointment( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPOINTMENT ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_APPOINTMENT ) );
        url.addParameter( PARAMETER_ID_APPOINTMENT, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_APPOINTMENT, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a appointment
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage appointments
     */
    @Action( ACTION_REMOVE_APPOINTMENT )
    public String doRemoveAppointment( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPOINTMENT ) );
        AppointmentHome.remove( nId );
        addInfo( INFO_APPOINTMENT_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_APPOINTMENTS );
    }

    /**
     * Returns the form to update info about a appointment
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_APPOINTMENT )
    public String getModifyAppointment( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPOINTMENT ) );

        if ( _appointment == null || ( _appointment.getId(  ) != nId ))
        {
            _appointment = AppointmentHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_APPOINTMENT, _appointment );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_APPOINTMENT, TEMPLATE_MODIFY_APPOINTMENT, model );
    }

    /**
     * Process the change form of a appointment
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_APPOINTMENT )
    public String doModifyAppointment( HttpServletRequest request )
    {
        populate( _appointment, request );

        // Check constraints
        if ( !validateBean( _appointment, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_APPOINTMENT, PARAMETER_ID_APPOINTMENT, _appointment.getId( ) );
        }

        AppointmentHome.update( _appointment );
        addInfo( INFO_APPOINTMENT_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_APPOINTMENTS );
    }
}