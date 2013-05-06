/* This file was generated by asn1gen */

package moo.model.ASNEXAMPLES;

import org.asn1gen.runtime.java.*;

import static org.asn1gen.runtime.java.Statics.*;

public class Item_journal extends Item {
  public final static Item_journal EMPTY = new Item_journal(Journal.EMPTY);

  public final Journal element;

  public Item_journal(final Journal element) {
    this.element = element;
  }

  public Journal element() {
    return this.element;
  }

  public int choiceId() {
    return 2;
  }

  @Override
  public Option<Journal> getJournal() {
    return some(this.element);
  }

  @Override
  public Item_journal withJournal(final Journal value) {
    return new Item_journal(value);
  }

  public String choiceName() {
    return "journal";
  }
}
