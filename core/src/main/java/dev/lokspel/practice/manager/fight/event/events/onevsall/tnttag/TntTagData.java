package dev.lokspel.practice.manager.fight.event.events.onevsall.tnttag;

import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import java.io.IOException;

public class TntTagData extends EventData {

  public TntTagData() {
    super(EventType.TNTTAG);
  }

  @Override
  protected void setCustomData() {}

  @Override
  protected void getCustomData() {}

  @Override
  protected void enable() throws IOException {}
}
