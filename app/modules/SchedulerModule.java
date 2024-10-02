/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package modules;

import com.google.inject.AbstractModule;
import it.drwolf.impaqtsbe.actors.DeleteTempPathActor;
import it.drwolf.impaqtsbe.cron.CronImpaqts;
import play.libs.akka.AkkaGuiceSupport;

public class SchedulerModule extends AbstractModule implements AkkaGuiceSupport {

	@Override
	protected void configure() {
		this.bindActor(DeleteTempPathActor.class, "delete-temp-path-content-actor");
		this.bind(CronImpaqts.class).asEagerSingleton();
	}
}
