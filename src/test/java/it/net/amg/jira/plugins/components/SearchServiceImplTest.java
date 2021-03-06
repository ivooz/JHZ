/*
 * Copyright 2015 AMG.net - Politechnika Łódzka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.net.amg.jira.plugins.components;

import net.amg.jira.plugins.jhz.services.SearchService;
import net.amg.jira.plugins.jhz.services.SearchServiceImpl;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ivo on 21/05/15.
 */
public class SearchServiceImplTest {

    SearchService searchService = new SearchServiceImpl();

    @Test
    public void getGroupedIssueTypes() {
        String ungroupedTypes = "test1|test2|test3|test4|test5|test1|bar1|bar2|bar3|bar4|bar5";
        Map<String,Set<String>> result = searchService.getGroupedIssueTypes(ungroupedTypes);
        assertEquals(5,result.size());
        for(int i=1; i<= 5;i++) {
            assertTrue(result.keySet().contains(SearchService.LABEL_BASE+i));
            assertEquals(2,result.get(SearchService.LABEL_BASE+i).size());
        }
    }

    @Test
    public void getGroupedIssueTypes2() {
        String ungroupedTypes = "a1|b1|c1|d5";
        Map<String,Set<String>> result = searchService.getGroupedIssueTypes(ungroupedTypes);
        assertEquals(3,result.get(SearchService.LABEL_BASE+1).size());
        assertEquals(1,result.get(SearchService.LABEL_BASE+5).size());
    }

    @Test
    public void getGroupedIssueTypes3() {
        String ungroupedTypes = "a99|b1|c1|d5";
        Map<String,Set<String>> result = searchService.getGroupedIssueTypes(ungroupedTypes);
        assertEquals(2,result.get(SearchService.LABEL_BASE+1).size());
        assertEquals(1,result.get(SearchService.LABEL_BASE+99).size());
        assertEquals(1,result.get(SearchService.LABEL_BASE+5).size());
    }
}
