/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.client.dataproviders;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.bpmn.forms.dataproviders.ruleflow.RuleFlowGroupDataChangedEvent;
import org.kie.workbench.common.stunner.forms.client.session.StunnerFormsHandler;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RuleFlowGroupDataProviderTest {

    @Mock
    private StunnerFormsHandler formsHandler;

    private RuleFlowGroupDataProvider tested;

    @Before
    public void setup() {
        tested = new RuleFlowGroupDataProvider(formsHandler);
    }

    @Test
    public void testOnRuleFlowGroupDataChanged() {
        RuleFlowGroupDataChangedEvent event = mock(RuleFlowGroupDataChangedEvent.class);
        when(event.getGroupNames()).thenReturn(new String[]{"g1", "g2"});
        tested.onRuleFlowGroupDataChanged(event);
        verify(formsHandler, times(1)).refreshCurrentSessionForms();
        List<String> values = tested.getRuleFlowGroupNames();
        assertNotNull(values);
        assertEquals(2, values.size());
        assertTrue(values.contains("g1"));
        assertTrue(values.contains("g2"));
    }
}
