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
package fr.paris.lutece.plugins.transparency.business;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the business class for the object Appointment
 */
public class Appointment implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{transparency.validation.appointment.Title.notEmpty}" )
    @Size( max = 255, message = "#i18n{transparency.validation.appointment.Title.size}" )
    private String _strTitle;

    private String _strDescription;

    @Past( message = "#i18n{transparency.validation.appointment.StartDate.past}" )
    private Date _dateStartDate;

    private Date _dateEndDate;

    private int _nTypeId;

    @Size( max = 255, message = "#i18n{transparency.validation.appointment.TypeLabel.size}" )
    private String _strTypeLabel;
    @URL( message = "#i18n{portal.validation.message.url}" )
    @Size( max = 255, message = "#i18n{transparency.validation.appointment.Url.size}" )
    private String _strUrl;

    private List<ElectedOfficial> electedOfficialList = new ArrayList<>( );
    private List<Lobby> lobbyList = new ArrayList<>( );

    private String _strContacts;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the Title
     * 
     * @return The Title
     */
    public String getTitle( )
    {
        return _strTitle;
    }

    /**
     * Sets the Title
     * 
     * @param strTitle
     *            The Title
     */
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }

    /**
     * Returns the Description
     * 
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * 
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the StartDate
     * 
     * @return The StartDate
     */
    public Date getStartDate( )
    {
        return _dateStartDate;
    }

    /**
     * Sets the StartDate
     * 
     * @param dateStartDate
     *            The StartDate
     */
    public void setStartDate( Date dateStartDate )
    {
        _dateStartDate = dateStartDate;
    }

    /**
     * Returns the EndDate
     * 
     * @return The EndDate
     */
    public Date getEndDate( )
    {
        return _dateEndDate;
    }

    /**
     * Sets the EndDate
     * 
     * @param dateEndDate
     *            The EndDate
     */
    public void setEndDate( Date dateEndDate )
    {
        _dateEndDate = dateEndDate;
    }

    /**
     * Returns the TypeId
     * 
     * @return The TypeId
     */
    public int getTypeId( )
    {
        return _nTypeId;
    }

    /**
     * Sets the TypeId
     * 
     * @param nTypeId
     *            The TypeId
     */
    public void setTypeId( int nTypeId )
    {
        _nTypeId = nTypeId;
    }

    /**
     * Returns the TypeLabel
     * 
     * @return The TypeLabel
     */
    public String getTypeLabel( )
    {
        return _strTypeLabel;
    }

    /**
     * Sets the TypeLabel
     * 
     * @param strTypeLabel
     *            The TypeLabel
     */
    public void setTypeLabel( String strTypeLabel )
    {
        _strTypeLabel = strTypeLabel;
    }

    /**
     * Returns the Url
     * 
     * @return The Url
     */
    public String getUrl( )
    {
        return _strUrl;
    }

    /**
     * Sets the Url
     * 
     * @param strUrl
     *            The Url
     */
    public void setUrl( String strUrl )
    {
        _strUrl = strUrl;
    }

    /**
     * get Elected Official List
     * 
     * @return Elected Official List
     */
    public List<ElectedOfficial> getElectedOfficialList( )
    {
        return electedOfficialList;
    }

    /**
     * set Elected Official List
     * 
     * @param electedOfficialList
     */
    public void setElectedOfficialList( List<ElectedOfficial> electedOfficialList )
    {
        this.electedOfficialList = electedOfficialList;
    }

    /**
     * get Lobby List
     * 
     * @return lobby List
     */
    public List<Lobby> getLobbyList( )
    {
        return lobbyList;
    }

    /**
     * set Lobby List
     * 
     * @param lobbyList
     */
    public void setLobbyList( List<Lobby> lobbyList )
    {
        this.lobbyList = lobbyList;
    }

    /**
     * get Contacts
     * 
     * @return the contacts
     */
    public String getContacts( )
    {
        return _strContacts;
    }

    /**
     * set Contacts
     * 
     * @param _strContacts
     */
    public void setContacts( String _strContacts )
    {
        this._strContacts = _strContacts;
    }

}
