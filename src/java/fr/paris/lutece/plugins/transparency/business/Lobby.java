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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the business class for the object Lobby
 */
public class Lobby implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{transparency.validation.lobby.Name.notEmpty}" )
    @Size( max = 255, message = "#i18n{transparency.validation.lobby.Name.size}" )
    private String _strName;

    private String _strNationalId;

    @Size( max = 50, message = "#i18n{transparency.validation.lobby.NationalIdType.size}" )
    private String _strNationalIdType;
    @URL( message = "#i18n{portal.validation.message.url}" )
    @Size( max = 255, message = "#i18n{transparency.validation.lobby.Url.size}" )
    private String _strUrl;

    private String _strJsonData;

    private Date _dateVersionDate;

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
     * Returns the Name
     * 
     * @return The Name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Sets the Name
     * 
     * @param strName
     *            The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the NationalId
     * 
     * @return The NationalId
     */
    public String getNationalId( )
    {
        return _strNationalId;
    }

    /**
     * Sets the NationalId
     * 
     * @param strNationalId
     *            The NationalId
     */
    public void setNationalId( String strNationalId )
    {
        _strNationalId = strNationalId;
    }

    /**
     * Returns the NationalIdType
     * 
     * @return The NationalIdType
     */
    public String getNationalIdType( )
    {
        return _strNationalIdType;
    }

    /**
     * Sets the NationalIdType
     * 
     * @param strNationalIdType
     *            The NationalIdType
     */
    public void setNationalIdType( String strNationalIdType )
    {
        _strNationalIdType = strNationalIdType;
    }

    /**
     * Returns the Url
     * 
     * @return The Url
     */
    @JsonIgnore
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
     * Returns the JsonData
     * 
     * @return The JsonData
     */
    @JsonIgnore
    public String getJsonData( )
    {
        return _strJsonData;
    }

    /**
     * Sets the JsonData
     * 
     * @param strJsonData
     *            The JsonData
     */
    public void setJsonData( String strJsonData )
    {
        _strJsonData = strJsonData;
    }

    /**
     * Returns the VersionDate
     * 
     * @return The VersionDate
     */
    @JsonIgnore
    public Date getVersionDate( )
    {
        return _dateVersionDate;
    }

    /**
     * Sets the VersionDate
     * 
     * @param dateVersionDate
     *            The VersionDate
     */
    public void setVersionDate( Date dateVersionDate )
    {
        _dateVersionDate = dateVersionDate;
    }

   
    /**
     * Get the JSON data formated in HTML
     * 
     * @return the html string
     */
    @JsonIgnore
    public String getHtmlData( )
    {
        if ( !StringUtils.isBlank( _strJsonData ) )
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            JsonNode jsonNode = null;

            // parse Json
            try 
            {
                jsonNode = mapper.readTree( _strJsonData );
            } 
            catch (IOException e) 
            {
                return e.getLocalizedMessage( );
            }
            
            // browse the tree to get an Html View
            return jsonToHtml(  jsonNode  );
        }
        else
        {
            return "";
        }
    }


    /**
     * convert json Data to structured Html text
     * 
     * @param json
     * @return string
     */
    private String jsonToHtml( JsonNode jsonNode )
    {
        StringBuilder html = new StringBuilder( );

        // print or iterate
        if ( jsonNode.isValueNode( ) )
        {
            // print the value
            html.append( jsonNode.toString( ) );
        }
        else if ( jsonNode.isArray( ) )
        {
            Iterator<JsonNode> nodeList = jsonNode.elements( );
            
            while (nodeList.hasNext( ) )
            {
                html.append( jsonToHtml( nodeList.next( ) ) );
            }
        } 
        else
        {
            Iterator<String> fields = jsonNode.fieldNames( );

            html.append( "<div class=\"json_object\">" );

            while ( fields.hasNext( ) ) 
            {
                String field = fields.next( );
                
                // print the key and open a DIV
                html.append( "<div><span class=\"json_title\">" ).append( field ).append( "</span> : " );

                JsonNode childNode = jsonNode.get( field ) ;
                // recursive call
                html.append( jsonToHtml(  childNode ) );
                // close the div
                html.append( "</div>" );

            }

            html.append( "</div>" );
        }    

        return html.toString( );
    }
}
