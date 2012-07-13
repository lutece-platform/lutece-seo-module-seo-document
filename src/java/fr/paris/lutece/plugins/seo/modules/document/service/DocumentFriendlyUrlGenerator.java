/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.seo.modules.document.service;

import fr.paris.lutece.plugins.document.business.Document;
import fr.paris.lutece.plugins.document.business.DocumentHome;
import fr.paris.lutece.plugins.document.business.publication.DocumentPublication;
import fr.paris.lutece.plugins.document.business.publication.DocumentPublicationHome;
import fr.paris.lutece.plugins.seo.business.FriendlyUrl;
import fr.paris.lutece.plugins.seo.service.*;

import java.sql.Date;

import java.text.MessageFormat;

import java.util.Collection;
import java.util.List;


/**
 * Document Alias Generator
 */
public class DocumentFriendlyUrlGenerator implements FriendlyUrlGenerator
{
    private static final String GENERATOR_NAME = "Document Alias Generator";
    private static final String TECHNICAL_URL = "/jsp/site/Portal.jsp?document_id={0}&portlet_id={1}";
    private static final String SLASH = "/";

    @Override
    public String generate( List<FriendlyUrl> list, GeneratorOptions options )
    {
        int nStatus = DocumentPublication.STATUS_PUBLISHED;
        Date date = new Date( 0 );
        Collection<DocumentPublication> pub = DocumentPublicationHome.findSinceDatePublishingAndStatus( date, nStatus );

        for ( DocumentPublication p : pub )
        {
            Document document = DocumentHome.findByPrimaryKey( p.getDocumentId(  ) );
            FriendlyUrl rule = new FriendlyUrl(  );
            rule.setFriendlyUrl( SLASH + FriendlyUrlUtils.convertToFriendlyUrl( document.getTitle(  ) ) );

            Object[] args = { p.getDocumentId(  ), p.getPortletId(  ) };
            String strTechnicalUrl = MessageFormat.format( TECHNICAL_URL, args );
            rule.setTechnicalUrl( strTechnicalUrl );
            list.add( rule );
        }

        return "";
    }

    @Override
    public String getName(  )
    {
        return GENERATOR_NAME;
    }
}
