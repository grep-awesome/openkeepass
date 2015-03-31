package de.slackspace.openkeepass.reader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.CompressionAlgorithm;
import de.slackspace.openkeepass.domain.CrsAlgorithm;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;
import de.slackspace.openkeepass.domain.KeePassHeader;
import de.slackspace.openkeepass.util.ByteUtils;

public class KeepassDatabaseReaderTest {

	@Test
	public void whenGettingEntriesByTitleShouldReturnMatchingEntries() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("target/test-classes/testDatabase.kdbx");
		
		KeePassDatabase reader = KeePassDatabase.getInstance(file);
		KeePassFile database = reader.openDatabase("abcdefg");
		
		Entry entry = database.getEntryByTitle("MyEntry");
		Assert.assertEquals("1v4QKuIUT6HHRkbq0MPL", entry.getPassword());
	}
	
	@Test
	public void whenGettingEntriesByTitleButNothingMatchesShouldReturnNull() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("target/test-classes/testDatabase.kdbx");
		
		KeePassDatabase reader = KeePassDatabase.getInstance(file);
		KeePassFile database = reader.openDatabase("abcdefg");
		
		Entry entry = database.getEntryByTitle("abcdefgh");
		Assert.assertNull(entry);
	}
	
	@Test
	public void whenKeePassFileIsV2ShouldReadHeader() throws IOException {
		FileInputStream file = new FileInputStream("target/test-classes/testDatabase.kdbx");
		
		KeePassDatabase reader = KeePassDatabase.getInstance(file);
		KeePassHeader header = reader.getHeader();
		
		Assert.assertTrue(Arrays.equals(ByteUtils.hexStringToByteArray("31C1F2E6BF714350BE5805216AFC5AFF"), header.getCipher()));
		Assert.assertEquals(CompressionAlgorithm.Gzip, header.getCompression());
		Assert.assertEquals(8000, header.getTransformRounds());
		Assert.assertTrue("EncryptionIV is not 2c605455f181fbc9462aefb817852b37", Arrays.equals(ByteUtils.hexStringToByteArray("2c605455f181fbc9462aefb817852b37"), header.getEncryptionIV()));
		Assert.assertTrue("StartBytes are not 69d788d9b01ea1facd1c0bf0187e7d74e4aa07b20d464f3d23d0b2dc2f059ff8", Arrays.equals(ByteUtils.hexStringToByteArray("69d788d9b01ea1facd1c0bf0187e7d74e4aa07b20d464f3d23d0b2dc2f059ff8"), header.getStreamStartBytes()));
		Assert.assertEquals(CrsAlgorithm.Salsa20, header.getCrsAlgorithm());
		Assert.assertTrue("MasterSeed is not 35ac8b529bc4f6e44194bccd0537fcb433a30bcb847e63156262c4df99c528ca", Arrays.equals(ByteUtils.hexStringToByteArray("35ac8b529bc4f6e44194bccd0537fcb433a30bcb847e63156262c4df99c528ca"), header.getMasterSeed()));
		Assert.assertTrue("TransformBytes are not 0d52d93efc5493ae6623f0d5d69bb76bd976bb717f4ee67abbe43528ebfbb646", Arrays.equals(ByteUtils.hexStringToByteArray("0d52d93efc5493ae6623f0d5d69bb76bd976bb717f4ee67abbe43528ebfbb646"), header.getTransformSeed()));
		Assert.assertTrue("ProtectedStreamKey is not ec77a2169769734c5d26e5341401f8d7b11052058f8455d314879075d0b7e257", Arrays.equals(ByteUtils.hexStringToByteArray("ec77a2169769734c5d26e5341401f8d7b11052058f8455d314879075d0b7e257"), header.getProtectedStreamKey()));
		Assert.assertEquals(210, header.getHeaderSize());
	}
	
	@Test
	public void whenPasswordIsValidShouldOpenKeepassFile() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("target/test-classes/testDatabase.kdbx");
		KeePassDatabase reader = KeePassDatabase.getInstance(file);
		
		KeePassFile database = reader.openDatabase("abcdefg");
		Assert.assertNotNull(database);
		
		Assert.assertEquals("TestDatabase", database.getMeta().getDatabaseName());
	}
	
	@Test(expected=UnsupportedOperationException.class) 
	public void whenKeePassFileIsOldShouldThrowException() {
		byte[] header = ByteUtils.hexStringToByteArray("03d9a29a65fb4bb5");
		
		ByteArrayInputStream file = new ByteArrayInputStream(header, header.length, 0);
		KeePassDatabase.getInstance(file);
	}
	
	@Test(expected=UnsupportedOperationException.class) 
	public void whenNotAKeePassFileShouldThrowException() {
		byte[] header = ByteUtils.hexStringToByteArray("0011223344556677");
		
		ByteArrayInputStream file = new ByteArrayInputStream(header, header.length, 0);
		KeePassDatabase.getInstance(file);
	}
	
	@Test
	public void testIfPasswordsCanBeDecrypted() throws FileNotFoundException {
		FileInputStream file = new FileInputStream("target/test-classes/fullBlownDatabase.kdbx");
		
		KeePassDatabase reader = KeePassDatabase.getInstance(file);
		KeePassFile database = reader.openDatabase("123456");
		
		List<Entry> entries = database.getEntries();
		
		Assert.assertEquals("2f29047129b9e4c48f05d09907e52b9b", entries.get(0).getPassword());
		Assert.assertEquals("GzteT206M4bVvHYaKPpA", entries.get(1).getPassword());
		Assert.assertEquals("gC03cizrzcBxytfKurWQ", entries.get(2).getPassword());
		Assert.assertEquals("jXjHEh3c8wcl0hank0qG", entries.get(3).getPassword());
		Assert.assertEquals("wkzB5KGIUoP8LKSSEngX", entries.get(4).getPassword());
	}
	
	@Test
	public void whenKeePassFileIsSecuredWithKeyFileShouldOpenKeePassFileWithKeyFile() throws FileNotFoundException {
		FileInputStream keePassFile = new FileInputStream("target/test-classes/DatabaseWithKeyfile.kdbx");
		FileInputStream keyFile = new FileInputStream("target/test-classes/DatabaseWithKeyfile.key");
		
		KeePassFile database = KeePassDatabase.getInstance(keePassFile).openDatabase(keyFile);
		
		List<Entry> entries = database.getEntries();
		Assert.assertEquals("V6uoqOm7esGRqm20VvMz", entries.get(0).getPassword());
	}
	
	@Test
	public void whenGettingInstanceByStringShouldOpenDatabase() {
		KeePassFile database = KeePassDatabase.getInstance("target/test-classes/fullBlownDatabase.kdbx").openDatabase("123456");
		List<Entry> entries = database.getEntries();
		Assert.assertEquals("2f29047129b9e4c48f05d09907e52b9b", entries.get(0).getPassword());
	}
	
	@Test
	public void whenGettingInstanceByFileShouldOpenDatabase() {
		KeePassFile database = KeePassDatabase.getInstance(new File("target/test-classes/fullBlownDatabase.kdbx")).openDatabase("123456");
		List<Entry> entries = database.getEntries();
		Assert.assertEquals("2f29047129b9e4c48f05d09907e52b9b", entries.get(0).getPassword());
	}
	
}
