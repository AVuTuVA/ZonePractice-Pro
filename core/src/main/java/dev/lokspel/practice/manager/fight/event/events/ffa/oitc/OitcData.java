package dev.lokspel.practice.manager.fight.event.events.ffa.oitc;

import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import dev.lokspel.practice.manager.fight.event.interfaces.EventFileData;
import java.io.IOException;

public class OitcData extends EventData {

  public OitcData() {
    super(EventType.OITC);
  }

  @Override
  protected void setCustomData(EventFileData data) {}

  @Override
  protected void getCustomData(EventFileData data) {}

  @Override
  protected void enable() throws IOException {}
}
