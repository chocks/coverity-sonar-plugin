/*
 * Coverity Sonar Plugin
 * Copyright (C) 2014 Coverity, Inc.
 * support@coverity.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.coverity.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.rules.XMLRuleParser;
import org.sonar.plugins.coverity.CoverityPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CoverityRules extends RuleRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CoverityRules.class);
    Settings settings;
    String domain;

    public CoverityRules(String language, String domain, Settings settings) {
        super(CoverityPlugin.REPOSITORY_KEY + "-" + language, language);
        this.domain = domain;
        this.settings = settings;
    }

    @Override
    public List<Rule> createRules() {
        List<Rule> rules;
        try {
            InputStream is = getClass().getResourceAsStream("/org/sonar/plugins/coverity/server/coverity-" + getLanguage() + ".xml");
            rules = new XMLRuleParser().parse(is);
            is.close();
        } catch(IOException e) {
            LOG.error("Failed to parse rules xml for language: " + getLanguage());
            e.printStackTrace();
            return new ArrayList<Rule>();
        }
        return rules;
    }
}
