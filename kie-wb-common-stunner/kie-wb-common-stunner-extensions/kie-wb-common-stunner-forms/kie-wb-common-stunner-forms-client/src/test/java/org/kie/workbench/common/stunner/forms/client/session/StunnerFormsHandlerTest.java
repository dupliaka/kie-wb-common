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

package org.kie.workbench.common.stunner.forms.client.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.client.api.SessionManager;
import org.kie.workbench.common.stunner.core.client.session.ClientSession;
import org.kie.workbench.common.stunner.forms.client.event.RefreshFormPropertiesEvent;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.uberfire.mocks.EventSourceMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StunnerFormsHandlerTest {

    @Mock
    private SessionManager sessionManager;

    @Mock
    private EventSourceMock<RefreshFormPropertiesEvent> refreshFormsEvent;

    private StunnerFormsHandler tested;

    @Before
    @SuppressWarnings("unchecked")
    public void init() {
        tested = new StunnerFormsHandler(sessionManager, refreshFormsEvent);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRefreshCurrentSessionForms() {
        ClientSession session = mock(ClientSession.class);
        when(sessionManager.getCurrentSession()).thenReturn(session);
        tested.refreshCurrentSessionForms();
        ArgumentCaptor<RefreshFormPropertiesEvent> eventCaptor = ArgumentCaptor.forClass(RefreshFormPropertiesEvent.class);
        verify(refreshFormsEvent, times(1)).fire(eventCaptor.capture());
        RefreshFormPropertiesEvent event = eventCaptor.getValue();
        assertEquals(session, event.getSession());
        assertNull(event.getUuid());
    }
}
