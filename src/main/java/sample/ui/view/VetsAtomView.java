/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.ui.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;

import lombok.extern.slf4j.Slf4j;
import sample.ui.model.Vet;
import sample.ui.model.Vets;

/**
 * A view creating a Atom representation from a list of Visit objects.
 *
 * @author Alef Arendsen
 * @author Arjen Poutsma
 * @author Arnaldo Piccinelli
 */
@Configuration
@Slf4j
public class VetsAtomView extends AbstractAtomFeedView {

	@Bean(name = "vets/vetList.atom")
	public VetsAtomView getVetsAtomView() {
		return this;
	}

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
		feed.setId("tag:springsource.org");
		feed.setTitle("Veterinarians");
		feed.setUpdated(new Date());
	}

	@Override
	protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("In buildFeedEntries: " + model);

		Vets vets = (Vets) model.get("vets");
		List<Vet> vetList = vets.getVetList();
		List<Entry> entries = new ArrayList<Entry>(vetList.size());

		for (Vet vet : vetList) {
			Entry entry = new Entry();
			// see
			// http://diveintomark.org/archives/2004/05/28/howto-atom-id#other
			entry.setId(String.format("tag:springsource.org,%s", vet.getId()));
			entry.setTitle(String.format("Vet: %s %s", vet.getFirstName(), vet.getLastName()));
			entry.setUpdated(new Date());

			Content summary = new Content();
			summary.setValue(vet.getSpecialties().toString());
			entry.setSummary(summary);

			entries.add(entry);
		}
		response.setContentType("blabla");
		return entries;
	}
}
